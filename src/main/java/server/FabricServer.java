package server;

import core.ArrowAllocator;
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

@CommandLine.Command(
        name = "fabric-server",
        mixinStandardHelpOptions = true,
        description = "Starts the fabric server",
        version = "0.1")
public class FabricServer implements Runnable {

    private static final FabricServer INSTANCE = new FabricServer();

    @Option(
            names = {"-p", "--port"},
            description = "Port to listen on (default: ${DEFAULT-VALUE})")
    private int port = 50051;

    @Option(
            names = {"-a", "--address"},
            description = "IP to listen on (default: ${DEFAULT-VALUE})")
    private String address = "localhost";

    private static final Logger logger = LoggerFactory.getLogger(FabricServer.class.getName());

    private Server server;
    private final ArrowAllocator allocator = new ArrowAllocator();
    private final DispatcherServiceGrpc.DispatcherServiceBlockingStub dispatcherServiceBlockingStub;
    ManagedChannel dispatcher_channel;

    public FabricServer() {
        dispatcher_channel =
                ManagedChannelBuilder.forTarget("localhost:50052")
                        // Channels are secure by default (via SSL/TLS). We disable TLS to avoid
                        // needing certifictes.
                        .usePlaintext()
                        .build();
        dispatcherServiceBlockingStub = DispatcherServiceGrpc.newBlockingStub(dispatcher_channel);
    }

    public void start() throws IOException {
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

    /** Await termination on the main thread since the grpc library uses daemon threads. */
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
