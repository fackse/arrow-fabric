package server;

import com.google.protobuf.ByteString;
import core.ArrowAllocator;
import core.Util;
import io.grpc.StatusRuntimeException;
import io.grpc.stub.StreamObserver;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.MessageFormat;
import org.apache.arrow.vector.FieldVector;
import org.apache.arrow.vector.ValueVector;
import org.example.ArrowFabric.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Just a reminder, this is the server side code ;)
 */
public class VectorServiceImpl extends VectorServiceGrpc.VectorServiceImplBase {

  private static final Logger logger = LoggerFactory.getLogger(VectorServiceImpl.class.getName());
  private final ArrowAllocator allocator;
  private final int grpcChunkSize = Integer.parseInt(Util.getConfig().getProperty("grpcChunkSize"));


  public VectorServiceImpl(ArrowAllocator new_allocator) {
    allocator = new_allocator;
  }

  @Override
  public void createVector(CreateVector request, StreamObserver<ServerStatus> responseObserver) {
    String name = request.getName();
    String type_of_vector = request.getType();
    Class<?> type = null;
    try {
      type = Class.forName(type_of_vector);
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    }
    try {
      allocator.createVector(name, type);
    } catch (Exception e) {
      e.printStackTrace();
    }
    ServerStatus reply = ServerStatus.newBuilder().setCode(Code.OK).build();
    responseObserver.onNext(reply);
    responseObserver.onCompleted();
    logger.info("Vector {} created", name);
  }

  @Override
  public void getVector(GetVector request, StreamObserver<SetVector> responseObserver) {
    String name = request.getName();
    String type = request.getType();
    FieldVector vector = (FieldVector) allocator.getVector(name, type);
    ByteArrayInputStream vectorStream = allocator.getStream(vector);
    byte[] content;
    int written = 0;
    int estimated = vectorStream.available();
    logger.info("[getVector] Start upload " + Util.getSizeFromBytes(estimated));
    while (true) {
      try {
        // This part may overflow memory
        if ((content = vectorStream.readNBytes(grpcChunkSize)).length != 0) {
          written += content.length;
          logger.info("[getVector] Uploaded " + Util.getSizeFromBytes(written));
          try {
            responseObserver.onNext(
                SetVector.newBuilder()
                    .setName(name)
                    .setType(type)
                    .setData(ByteString.copyFrom(content))
                    .build());
          } catch (StatusRuntimeException e) {
            logger.error("[getVector] RPC failed: {}", e.getStatus());
          } catch (OutOfMemoryError e){
            logger.error("OUT OF MEMORY!!!!");
          }
        } else {
          // If nothing more can be read
          break;
        }
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    assert written == estimated;
    responseObserver.onCompleted();
  }

  @Override
  public StreamObserver<SetVector> setVector(StreamObserver<ServerStatus> responseObserver) {
    return new StreamObserver<>() {
      final ByteArrayOutputStream incoming = new ByteArrayOutputStream();
      ServerStatus reply;
      String name;
      String type;

      @Override
      public void onNext(SetVector vectorChunk) {
        name = vectorChunk.getName();
        type = vectorChunk.getType();
        try {
          logger.info(
              "[setVector] Received {}", Util.getSizeFromBytes(vectorChunk.getData().size()));
          incoming.write(vectorChunk.getData().toByteArray());
        } catch (IOException e) {
          logger.error("[setVector] Write chunks failed. Reason: " + e.getMessage());
          reply =
              ServerStatus.newBuilder()
                  .setCode(Code.ERROR)
                  .setMessage("Write chunks failed. Reason: " + e.getMessage())
                  .build();
          responseObserver.onNext(reply);
          responseObserver.onCompleted();
        }
      }

      @Override
      public void onError(Throwable t) {
        logger.error("RPC failed: {}", t.getMessage());
        t.printStackTrace();
      }

      @Override
      public void onCompleted() {
        logger.info(
            "[setVector] All data received. Total size of payload {}",
            Util.getSizeFromBytes(incoming.size()));

        ServerStatus reply;
        if (allocator.readFromStream(incoming, name, type) != null) {
          reply = ServerStatus.newBuilder().setCode(Code.OK).setMessage("Vector set").build();
        } else {
          reply =
              ServerStatus.newBuilder()
                  .setCode(Code.ERROR)
                  .setMessage(MessageFormat.format("Vector with name {} failed", name))
                  .build();
        }
        responseObserver.onNext(reply);
        responseObserver.onCompleted();
        logger.info("[setVector] Vector {} set!", name);
      }
    };
  }

  @Override
  public void deleteVector(GetVector request, StreamObserver<ServerStatus> responseObserver) {
    String name = request.getName();
    String type = request.getType();
    ValueVector v;
    ServerStatus reply;
    if (allocator.removeVector(name, type)) {
      logger.info("Vector {} deleted!", request.getName());
      reply = ServerStatus.newBuilder().setCode(Code.OK).build();
    } else {
      reply =
          ServerStatus.newBuilder()
              .setCode(Code.ERROR)
              .setMessage(MessageFormat.format("Vector with name {} not found}", name))
              .build();
    }
    responseObserver.onNext(reply);
    responseObserver.onCompleted();
  }
}
