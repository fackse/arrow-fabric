package dispatcher;

import io.grpc.Server;
import io.grpc.netty.shaded.io.grpc.netty.NettyServerBuilder;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.exceptions.JedisConnectionException;

/**
 * Main class for dispatcher logic
 */
@Command(
    name = "fabric-dispatcher",
    mixinStandardHelpOptions = true,
    description = "Starts the fabric dispatcher",
    version = "0.1")
@SuppressWarnings("FieldMayBeFinal")
public class FabricDispatcher implements Callable<Integer> {

  @Option(
      names = {"--port"},
      description = "Port to listen on (default: ${DEFAULT-VALUE})")
  int port = 50052;

  @Option(
      names = {"--address"},
      description = "IP to listen on (default: ${DEFAULT-VALUE})")
  String address = "localhost";

  @Option(
      names = {"--redis-address"},
      description = "Address of redis (default: ${DEFAULT-VALUE})")
  String redisAddress = "localhost";

  @Option(
      names = {"--redis-port"},
      description = "Port of redis (default: ${DEFAULT-VALUE})")
  int redisPort = 6379;

  private static final FabricDispatcher INSTANCE = new FabricDispatcher();


  /**
   * Logging instance
   */
  private static final Logger logger = LoggerFactory.getLogger(FabricDispatcher.class.getName());

  /**
   * Grpc server
   */
  private Server server;

  /**
   * Jedis Pool for operations on redis instance
   */
  private JedisPool jedisPool;

  /**
   * {@link ScheduledExecutorService} is called within a time range to run server heartbeat
   */
  private ScheduledExecutorService executor;

  /**
   * Starts all dispatcher services
   *
   * @throws IOException If something went wrong with the {@link NettyServerBuilder}
   */
  public void startService() throws IOException {
    jedisPool = new JedisPool(redisAddress, redisPort);
    DispatcherServiceImpl dispatcher;
    try {
      dispatcher = new DispatcherServiceImpl(jedisPool);
    } catch (JedisConnectionException e) {
      logger.error("Can't connect to REDIS Server {}:{}", redisAddress, redisPort);
      return;
    }

    // Thread to watch if servers needs to be removed from list
    executor = Executors.newScheduledThreadPool(1);
    executor.scheduleAtFixedRate(dispatcher, 0, 3, TimeUnit.SECONDS);

    server =
        NettyServerBuilder.forAddress(new InetSocketAddress(address, port))
            .addService(dispatcher)
            .build()
            .start();
    logger.info("Dispatcher started, listening on " + address + ":" + port);
    Runtime.getRuntime()
        .addShutdownHook(
            new Thread(
                () -> {
                  // Use stderr here since the logger may have been reset by its JVM shutdown hook.
                  System.err.println("*** shutting down gRPC server since JVM is shutting down");
                  try {
                    FabricDispatcher.this.stop();
                  } catch (InterruptedException e) {
                    e.printStackTrace(System.err);
                  }
                  System.err.println("*** server shut down");
                }));
  }

  /**
   * Stops Grpc server
   *
   * @throws InterruptedException If termination fails
   */
  private void stop() throws InterruptedException {
    if (server != null) {
      server.shutdown().awaitTermination(30, TimeUnit.SECONDS);
    }
  }

  /**
   * Await termination on the main thread since the grpc library uses daemon threads.
   *
   * @return Returns 1 if everything went fine
   * @throws InterruptedException If termination fails
   */
  public Integer blockUntilShutdown() throws InterruptedException {
    if (server != null) {
      server.awaitTermination();
    }
    jedisPool.close();
    executor.shutdownNow();
    return 1;
  }

  /**
   * Starts dispatcher server according config from commandline options
   *
   * @param args User supplied parameters
   * @throws IOException          If something in the subroutine fails
   * @throws InterruptedException If something in the subroutine fails
   */
  public static void main(String... args) throws IOException, InterruptedException {
    int exitCode = new CommandLine(INSTANCE).execute(args);
    System.exit(exitCode);
  }

  /**
   * Is called by picocli command line interface
   *
   * @return Returns 1 if everything went fine
   * @throws Exception If something in the subroutine fails
   */
  @Override
  public Integer call() throws Exception {
    // Server code
    INSTANCE.startService();
    return INSTANCE.blockUntilShutdown();
  }
}
