package core;

import io.grpc.Server;
import io.grpc.stub.StreamObserver;
import org.example.ArrowFabric.*;
import com.google.protobuf.ByteString;

import java.nio.ByteBuffer;
import java.text.MessageFormat;
import java.util.logging.Logger;

public class IntVectorServiceImpl extends IntVectorServiceGrpc.IntVectorServiceImplBase {

    private static final Logger logger = Logger.getLogger(IntVectorServiceImpl.class.getName());
    private Server server;
    private final ArrowAllocator allocator;

    public IntVectorServiceImpl(ArrowAllocator new_allocator) {
        allocator = new_allocator;
    }

    /**
     * @param request          s
     * @param responseObserver s
     */
    @Override
    public void create(CreateIntVector request, StreamObserver<Status> responseObserver) {
        String name = request.getName();
        allocator.newIntVector(name);
        Status reply = Status.newBuilder().setCode(Code.OK).build();
        responseObserver.onNext(reply);
        responseObserver.onCompleted();
    }

    /**
     * @param request          s
     * @param responseObserver s
     */
    @Override
    public void set(SetIntVector request, StreamObserver<Status> responseObserver) {
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
     * @param request          s
     * @param responseObserver s
     */
    @Override
    public void get(GetIntVector request, StreamObserver<IntVector> responseObserver) {
        String name = request.getName();
        // TODO check if cast from long to int is safe..
        int index = (int) request.getIndex();
        IntVector reply;
        if (allocator.vectors.containsKey(name)) {
            logger.info(MessageFormat.format("Vector {0} found", name));
            // TODO Check whether setSafe is "really" safe. Whats the behaviour when index is larger then the vector?
            int value = allocator.vectors.get(name).get(index);
            Status status = Status.newBuilder().setMessage("OK").setCode(Code.OK).build();
            reply = IntVector.newBuilder().setStatus(status).setValue(value).build();
        } else {
            Status status = Status.newBuilder().setMessage("FAIL!").setCode(Code.ERROR).build();
            reply = IntVector.newBuilder().setStatus(status).setValue(-1).build();
        }
        responseObserver.onNext(reply);
        responseObserver.onCompleted();
    }
}
