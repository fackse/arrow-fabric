package core;

import com.google.protobuf.ByteString;
import io.grpc.Server;
import io.grpc.stub.StreamObserver;
import org.apache.arrow.vector.FieldVector;
import org.apache.arrow.vector.ValueVector;
import org.apache.arrow.vector.VectorSchemaRoot;
import org.apache.arrow.vector.complex.reader.FieldReader;
import org.apache.arrow.vector.ipc.ArrowStreamWriter;
import org.apache.arrow.vector.types.pojo.Field;
import org.example.ArrowFabric.*;

import java.io.ByteArrayOutputStream;
import java.nio.channels.Channels;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

public class VectorServiceImpl extends VectorServiceGrpc.VectorServiceImplBase {

    private static final Logger logger = Logger.getLogger(VectorServiceImpl.class.getName());
    private Server server;
    private final ArrowAllocator allocator;

    public VectorServiceImpl(ArrowAllocator new_allocator) {
        allocator = new_allocator;
    }

    /**
     * @param request
     * @param responseObserver
     */
    @Override
    public void create(CreateVector request, StreamObserver<Status> responseObserver) {
        String name = request.getName();
        allocator.newVector(name);
        Status reply = Status.newBuilder().setCode(Code.OK).build();
        responseObserver.onNext(reply);
        responseObserver.onCompleted();
    }

    /**
     * @param request
     * @param responseObserver
     */
    @Override
    public void set(SetVector request, StreamObserver<Status> responseObserver) {
        String name = request.getName();
        // TODO check if cast from long to int is safe..
        int index = (int) request.getIndex();
        int value = (int) request.getValue();
        Status reply;
        if (allocator.vectors.containsKey(name)) {
            logger.info(MessageFormat.format("Vector {0} found", name));
            // TODO Check whether setSafe is "really" safe. Whats the behaviour when index is larger then the vector?
            allocator.vectors.get(name).setSafe(index, value);
            logger.info(String.valueOf(allocator.vectors.get(name).get(index)));
            reply = Status.newBuilder().setCode(Code.OK).build();
        } else {
            reply = Status.newBuilder().setCode(Code.ERROR).setMessage(MessageFormat.format("Vector with name {0} unknown", name)).build();
        }
        responseObserver.onNext(reply);
        responseObserver.onCompleted();
    }

    /**
     * @param request
     * @param responseObserver
     */
    @Override
    public void get(GetVector request, StreamObserver<Vector> responseObserver) {
        String name = request.getName();
        FieldVector v = allocator.vectors.get(name);
        FieldReader reader = v.getReader();
        Vector vector = Vector.newBuilder().setData(
                ByteString.copyFrom(reader.readByteArray())
        ).setType("lol").build();
        responseObserver.onNext(vector);
        responseObserver.onCompleted();
    }
}
