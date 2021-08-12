package dispatcher;


import core.DispatcherServiceImpl;
import core.IntVectorServiceImpl;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import server.FabricServer;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

public class Dispatcher {
    private static final Logger logger = Logger.getLogger(Dispatcher.class.getName());
    private Server server;


    public void start() throws IOException {
        int port = 50052;
        server = ServerBuilder
                .forPort(port)
                .addService(new DispatcherServiceImpl())//TODO For now hard coded. Will be dynamic in the future
                .build()
                .start();
        logger.info("Server started, listening on " + port);
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            // Use stderr here since the logger may have been reset by its JVM shutdown hook.
            System.err.println("*** shutting down gRPC server since JVM is shutting down");
            try {
                Dispatcher.this.stop();
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
        // Server code
        Dispatcher server = new Dispatcher();
        server.start();
        server.blockUntilShutdown();
    }

}
