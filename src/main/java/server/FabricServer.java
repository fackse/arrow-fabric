package server;

import core.ArrowAllocator;
import core.Util;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.Server;
import io.grpc.StatusRuntimeException;
import io.grpc.netty.shaded.io.grpc.netty.NettyServerBuilder;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.TimeUnit;
import org.example.ArrowFabric.DispatcherServiceGrpc;
import org.example.ArrowFabric.ServerInfo;
import org.example.ArrowFabric.ServerStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import picocli.CommandLine;
import picocli.CommandLine.Option;

@SuppressWarnings("FieldMayBeFinal")
@CommandLine.Command(
    name = "fabric-server",
    mixinStandardHelpOptions = true,
    description = "Starts the fabric server",
    version = "0.1")
public class FabricServer implements Runnable {

  private static final FabricServer INSTANCE = new FabricServer();
  // This little headroom is needed in order to copy data from local memory to grpc data stream
  int dataHeadroom = Integer.parseInt(Util.getConfig().getProperty("dataHeadroom"));

  @Option(
      names = {"-p", "--port"},
      description = "Port to listen on (default: ${DEFAULT-VALUE})")
  private int port = 50051;

  @Option(
      names = {"-a", "--address"},
      description = "IP to listen on (default: ${DEFAULT-VALUE})")
  private String address = "localhost";

  @Option(
      names = {"--dispatcher-port"},
      description = "Port of dispatcher (default: ${DEFAULT-VALUE})")
  private int dispatcherPort = 50052;

  @Option(
      names = {"--dispatcher-address"},
      description = "Address of (default: ${DEFAULT-VALUE})")
  private String dispatcherAddress = "localhost";

  @Option(
      names = {"--max-memory"},
      description = "Maximum memory in bytes (default: ${DEFAULT-VALUE})")
  private long maxMemory = Runtime.getRuntime().maxMemory() - dataHeadroom;

  private static final Logger logger = LoggerFactory.getLogger(FabricServer.class.getName());
  private Server server;
  private DispatcherServiceGrpc.DispatcherServiceBlockingStub dispatcherServiceBlockingStub;
  ManagedChannel dispatcher_channel;

  public void start() throws IOException {
    ArrowAllocator allocator = new ArrowAllocator(maxMemory);
    dispatcher_channel =
        ManagedChannelBuilder.forTarget(dispatcherAddress+":"+dispatcherPort)
            // Channels are secure by default (via SSL/TLS). We disable TLS to avoid
            // needing certificates.
            .usePlaintext()
            .build();
    dispatcherServiceBlockingStub = DispatcherServiceGrpc.newBlockingStub(dispatcher_channel);
    server =
        NettyServerBuilder.forAddress(new InetSocketAddress(address, port))
            .addService(new VectorServiceImpl(allocator))
            .addService(new HeartbeatServiceImpl(allocator))
            .build()
            .start();
    logger.info("Server started, listening on " + address + ":" + port);
    ServerInfo request =
        ServerInfo.newBuilder()
            .setAddress(address)
            .setPort(port)
            .setLimit(allocator.rootAllocator.getLimit())
            .setAllocatedMemory(allocator.rootAllocator.getAllocatedMemory())
            .setNumVectors(allocator.numVectors())
            .setHeadroom(allocator.rootAllocator.getHeadroom())
            .build();
    ServerStatus response;
    try {
      response = dispatcherServiceBlockingStub.registerServer(request);
      logger.info("[registerServer] Code: " + response.getCode());
    } catch (StatusRuntimeException e) {
      logger.error("RPC failed: {}", e.getStatus());
    }
    Runtime.getRuntime()
        .addShutdownHook(
            new Thread(
                () -> {
                  // Use stderr here since the logger may have been reset by its JVM shutdown hook.
                  System.err.println("*** shutting down gRPC server since JVM is shutting down");
                  try {
                    FabricServer.this.stop();
                  } catch (InterruptedException e) {
                    e.printStackTrace(System.err);
                  }
                  System.err.println("*** server shut down");
                }));
  }

  private void stop() throws InterruptedException {
    if (server != null) {
      server.shutdown().awaitTermination(30, TimeUnit.SECONDS);
    }
  }

  /**
   * Await termination on the main thread since the grpc library uses daemon threads.
   */
  public void blockUntilShutdown() throws InterruptedException {
    if (server != null) {
      server.awaitTermination();
    }
  }

  public static void main(String[] args) throws IOException, InterruptedException {
    int exitCode = new CommandLine(INSTANCE).execute(args);
    System.exit(exitCode);
  }

  @Override
  public void run() {
    // Server code
    try {
      INSTANCE.start();
      INSTANCE.blockUntilShutdown();
    } catch (IOException | InterruptedException e) {
      e.printStackTrace();
    }
  }
}
