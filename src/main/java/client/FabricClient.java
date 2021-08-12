package client;

import core.ArrowAllocator;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;
import org.apache.arrow.vector.FieldVector;
import org.apache.arrow.vector.ValueVector;
import org.apache.arrow.vector.IntVector;
import org.apache.arrow.vector.VectorSchemaRoot;
import org.apache.arrow.vector.ipc.ArrowStreamReader;
import org.apache.arrow.vector.types.pojo.Field;
import org.apache.arrow.vector.types.pojo.Schema;
import org.example.ArrowFabric.*;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FabricClient implements AutoCloseable {
    private static final Logger logger = Logger.getLogger(FabricClient.class.getName());
    private final DispatcherServiceGrpc.DispatcherServiceBlockingStub dispatcherServiceBlockingStub;
    ManagedChannel dispatcher_channel;
    private final ArrowAllocator allocator = new ArrowAllocator();

    public FabricClient(String dispatcher_address) {
        dispatcher_channel = ManagedChannelBuilder.forTarget(dispatcher_address)
                // Channels are secure by default (via SSL/TLS). For the example we disable TLS to avoid
                // needing certificates.
                .usePlaintext()
                .build();
        dispatcherServiceBlockingStub = DispatcherServiceGrpc.newBlockingStub(dispatcher_channel);
    }

    public String[] getTicket(String type, String name) {
        GetTicket request = GetTicket.newBuilder().setType("int").setName(name).build();
        Ticket ticket;
        try {
            ticket = dispatcherServiceBlockingStub.getTicket(request);
        } catch (StatusRuntimeException e) {
            logger.log(Level.WARNING, "RPC failed: {0}", e.getStatus());
            return new String[0];
        }
        logger.info("[getTicket] Transaction ID: " + ticket.getTransactionId() + "@ " + ticket.getServer());
        String[] response = new String[2];
        response[0] = ticket.getTransactionId();
        response[1] = ticket.getServer();
        return response;
    }

    @Override
    public void close() throws Exception {
        // ManagedChannels use resources like threads and TCP connections. To prevent leaking these
        // resources the channel should be shut down when it will no longer be used. If it may be used
        // again leave it running.
        dispatcher_channel.shutdownNow().awaitTermination(5, TimeUnit.SECONDS);
    }

    private class Handler implements AutoCloseable {
        private String server_address;
        private String transaction_id;
        private VectorServiceGrpc.VectorServiceBlockingStub serverServiceBlockingStub;
        private ManagedChannel server_channel;

        public Handler() {
        }

        public Handler on(Class<?> type_of_vector, String name) {
            assert type_of_vector.isInstance(ValueVector.class);
            // get Ticket from dispatcher
            String[] data = FabricClient.this.getTicket(type_of_vector.getName(), name);
            transaction_id = data[0];
            server_address = data[1];
            open_channel();
            return this;
        }


        public void createVector(String name) {
            logger.info("Will try to create vector " + name + " ...");
            CreateVector request = CreateVector.newBuilder().setName(name).build();
            Status response;
            try {
                response = serverServiceBlockingStub.create(request);
            } catch (StatusRuntimeException e) {
                logger.log(Level.WARNING, "RPC failed: {0}", e.getStatus());
                return;
            }
            logger.info("[createVector] Code: " + response.getCode() + " Answer: " + response.getMessage());
        }

        public void setVector(String name, int index, int value) {
            logger.info("Will try to set vector " + name + " ...");
            SetVector request = SetVector.newBuilder()
                    .setName(name)
                    .setIndex(index)
                    .setValue(value)
                    .build();
            Status response;
            try {
                response = serverServiceBlockingStub.set(request);
            } catch (StatusRuntimeException e) {
                logger.log(Level.WARNING, "RPC failed: {0}", e.getStatus());
                return;
            }
            logger.info("[setVector] Code: " + response.getCode() + " Answer: " + response.getMessage());
        }

        public ValueVector getVector(String name) throws IOException {
            logger.info("Will try to get vector " + name + " ...");
            GetVector request = GetVector.newBuilder()
                    .setName(name)
                    .build();
            Vector response;
            FieldVector v = null;
            try {
                response = serverServiceBlockingStub.get(request);
                try (ArrowStreamReader reader = new ArrowStreamReader(new ByteArrayInputStream(response.getData().toByteArray()), FabricClient.this.allocator.getAllocator())) {
                    Schema schema = reader.getVectorSchemaRoot().getSchema();
                    VectorSchemaRoot readBatch = reader.getVectorSchemaRoot();
                    reader.loadNextBatch();
                    v = readBatch.getVector(name);
                }

            } catch (StatusRuntimeException e) {
                logger.log(Level.WARNING, "RPC failed: {0}", e.getStatus());
            }
            //logger.info("[getVector] Code: " + response.getValue() + " Answer: " + response.getStatus());
            return v;
        }


        private void open_channel() {
            //FabricClient.this.getTicket();
            // Channels are secure by default (via SSL/TLS). For the example we disable TLS to avoid
            // needing certificates.
            // Channels are secure by default (via SSL/TLS). For the example we disable TLS to avoid
            // needing certificates.
            server_channel = ManagedChannelBuilder.forTarget(server_address)
                    // Channels are secure by default (via SSL/TLS). For the example we disable TLS to avoid
                    // needing certificates.
                    .usePlaintext()
                    .build();
            serverServiceBlockingStub = VectorServiceGrpc.newBlockingStub(server_channel);
        }

        @Override
        public void close() throws Exception {
            server_channel.shutdownNow().awaitTermination(5, TimeUnit.SECONDS);
        }
    }

    public void main(String[] args) throws Exception {
        String dispatcher = "localhost:50052";
        // open channel to dispatcher as resource
        try (FabricClient client = new FabricClient(dispatcher)) {
            // open write channel as resource
            try (Handler handler = new Handler().on(IntVector.class, "name1")) {
                handler.createVector("V1");
                handler.setVector("V1", 1, 2);
            }
        }
    }

}
