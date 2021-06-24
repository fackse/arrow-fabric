package client;

import com.google.protobuf.ByteString;
import io.grpc.Channel;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;
import org.example.ArrowFabric.*;

import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FabricClient {
    private static final Logger logger = Logger.getLogger(FabricClient.class.getName());

    private final IntVectorServiceGrpc.IntVectorServiceBlockingStub blockingStub;

    public FabricClient(Channel channel) {
        // 'channel' here is a Channel, not a ManagedChannel, so it is not this code's responsibility to
        // shut it down.
        // Passing Channels to code makes code easier to test and makes it easier to reuse Channels.
        blockingStub = IntVectorServiceGrpc.newBlockingStub(channel);
    }

    public void createVector(String name){
        logger.info("Will try to create vector " + name + " ...");
        CreateIntVector request = CreateIntVector.newBuilder().setName(name).build();
        Status response;
        try {
            response = blockingStub.create(request);
        } catch (StatusRuntimeException e) {
            logger.log(Level.WARNING, "RPC failed: {0}", e.getStatus());
            return;
        }
        logger.info("[createVector] Code: " + response.getCode() + " Answer: "+ response.getMessage());
    }

    public void setVector(String name, int index, int value){
        logger.info("Will try to set vector " + name + " ...");
        SetIntVector request = SetIntVector.newBuilder()
                .setName(name)
                .setIndex(index)
                .setValue(value)
                .build();
        Status response;
        try {
            response = blockingStub.set(request);
        } catch (StatusRuntimeException e) {
            logger.log(Level.WARNING, "RPC failed: {0}", e.getStatus());
            return;
        }
        logger.info("[setVector] Code: " + response.getCode() + " Answer: "+ response.getMessage());
    }

    public void getVector(String name, int index){
        logger.info("Will try to get vector " + name + " ...");
        GetIntVector request = GetIntVector.newBuilder()
                .setName(name)
                .setIndex(index)
                .build();
        IntVector response;
        try {
            response = blockingStub.get(request);
        } catch (StatusRuntimeException e) {
            logger.log(Level.WARNING, "RPC failed: {0}", e.getStatus());
            return;
        }
        logger.info("[getVector] Code: " + response.getValue() + " Answer: "+ response.getStatus());
    }


    public static void main(String[] args) throws Exception {
        String name = "name1";
        // Access a service running on the local machine on port 50051
        String target = "localhost:50051";
        // Allow passing in the user and target strings as command line arguments

        // Create a communication channel to the server, known as a Channel. Channels are thread-safe
        // and reusable. It is common to create channels at the beginning of your application and reuse
        // them until the application shuts down.
        ManagedChannel channel = ManagedChannelBuilder.forTarget(target)
                // Channels are secure by default (via SSL/TLS). For the example we disable TLS to avoid
                // needing certificates.
                .usePlaintext()
                .build();
        try {
            FabricClient client = new FabricClient(channel);
            client.createVector(name);
            client.setVector("name1", 0, 1337);
            client.getVector("name1", 0);
        } finally {
            // ManagedChannels use resources like threads and TCP connections. To prevent leaking these
            // resources the channel should be shut down when it will no longer be used. If it may be used
            // again leave it running.
            channel.shutdownNow().awaitTermination(5, TimeUnit.SECONDS);
        }
    }
}
