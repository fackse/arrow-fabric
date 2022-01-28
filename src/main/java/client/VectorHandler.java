package client;

import static org.example.ArrowFabric.OP.OP_WRITE;

import com.google.protobuf.ByteString;
import core.Util;
import core.exceptions.ServerException;
import io.grpc.*;
import io.grpc.stub.StreamObserver;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import org.apache.arrow.vector.FieldVector;
import org.example.ArrowFabric.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Using the handler, CRD operations can be performed on a single vector. Uses local {@link
 * core.ArrowAllocator}
 */
public class VectorHandler implements AutoCloseable {
  /** The fabric client owns the {@link core.ArrowAllocator} */
  private final FabricClient fabricClient;
  /** Several {@link OP}s can be performed on a vector. Each VectorHandler is bound to one OP. */
  private final OP op;
  /**
   * Each vector has its type. Besides its name, the type is also important to distinguish its
   * uniqueness across the cluster.
   */
  private final Class<?> type_of_vector;
  /**
   * Each vector hat its name. In conjunction with its type, the vector is unique across the
   * cluster.
   */
  private final String vectorName;
  /**
   * gRPC {@link org.example.ArrowFabric.VectorServiceGrpc.VectorServiceBlockingStub} to talk to the
   * selected server
   */
  private VectorServiceGrpc.VectorServiceBlockingStub serverServiceBlockingStub;
  /**
   * {@link org.example.ArrowFabric.VectorServiceGrpc.VectorServiceStub} to talk to the desired
   * server
   */
  private VectorServiceGrpc.VectorServiceStub serverServiceStub;
  /** gRPC {@link ManagedChannel} to talk to the server */
  private ManagedChannel server_channel;
  /**
   * gRPC {@link org.example.ArrowFabric.DispatcherServiceGrpc.DispatcherServiceBlockingStub} to
   * talk to the dispatcher
   */
  private final DispatcherServiceGrpc.DispatcherServiceBlockingStub dispatcherServiceBlockingStub;
  /** gRPC {@link ManagedChannel} to talk to the FabricDispatcher */
  ManagedChannel dispatcher_channel;
  private static final Logger logger = LoggerFactory.getLogger(VectorHandler.class.getName());
  /**
   * Switch to control if locally created vectors wil be deleted after this handler will be closed.
   */
  private boolean localGC = false;

  /**
   * @param fabricClient The fabric client connects to the cluster. And, as a result, with the
   *     dispatcher responsible for the cluster coordination.
   * @param dispatcherAddress Address of the dispatcher
   * @param dispatcherPort Port of the dispatcher
   * @param type_of_vector Type of vector
   * @param name Name of vector
   * @param op Type of {@link OP}
   */
  public VectorHandler(
      FabricClient fabricClient,
      String dispatcherAddress,
      int dispatcherPort,
      Class<?> type_of_vector,
      String name,
      OP op) {
    this.fabricClient = fabricClient;
    this.op = op;
    String dispatcher_address = dispatcherAddress + ":" + dispatcherPort;
    this.type_of_vector = type_of_vector;
    this.vectorName = name;

    dispatcher_channel =
        ManagedChannelBuilder.forTarget(dispatcher_address)
            // Channels are secure by default (via SSL/TLS). We disable TLS to avoid
            // needing certificates.
            .usePlaintext()
            .build();
    dispatcherServiceBlockingStub = DispatcherServiceGrpc.newBlockingStub(dispatcher_channel);
  }

  /**
   * Creates local Vector backed by allocator
   *
   * @return Arrow Vector
   * @throws Exception If Type of Vector is not implemented in Fabric
   */
  public <T> T createLocalVector() throws Exception {
    return fabricClient.allocator.createVector(vectorName, type_of_vector);
  }

  /**
   * @return Arrow Vector backed by local storage
   * @throws IOException If data cannot be deserialized into vector
   * @throws ServerException If no server is found which has this vector
   */
  public FieldVector getVector() throws IOException, ServerException {
    GetServer serverRequest =
        GetServer.newBuilder()
            .setType(type_of_vector.getName())
            .setName(vectorName)
            .setOp(op)
            .build();
    getServer(serverRequest);

    logger.info("Will try to get vector " + vectorName + " ...");
    GetVector request =
        GetVector.newBuilder().setName(vectorName).setType(type_of_vector.getName()).build();
    Iterator<SetVector> responseIterator;
    ByteArrayOutputStream incoming = new ByteArrayOutputStream();
    try {
      responseIterator = serverServiceBlockingStub.getVector(request);
      for (Iterator<SetVector> it = responseIterator; it.hasNext(); ) {
        SetVector vectorChunk = it.next();
        incoming.write(vectorChunk.getData().toByteArray());
      }
      return (FieldVector)
          fabricClient.allocator.readFromStream(incoming, vectorName, type_of_vector.getName());
    } catch (StatusRuntimeException e) {
      logger.warn("RPC failed: {}", e.getStatus());
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }

  /**
   * Deletes vector on server
   *
   * @return Boolean which indicates whether delete operation was successful or not
   * @throws ServerException If no server is found which has this vector
   */
  public boolean deleteVector() throws ServerException {
    boolean result = false;
    GetServer serverRequest =
        GetServer.newBuilder()
            .setType(type_of_vector.getName())
            .setName(vectorName)
            .setOp(op)
            .build();
    getServer(serverRequest);

    logger.info("Will try to delete vector " + vectorName + " ...");
    GetVector request =
        GetVector.newBuilder().setName(vectorName).setType(type_of_vector.getName()).build();
    ServerStatus response;
    try {
      response = serverServiceBlockingStub.deleteVector(request);
      result = response.getCode().equals(Code.OK);
    } catch (StatusRuntimeException e) {
      logger.error("RPC failed: {}", e.getStatus());
    } catch (Exception e) {
      e.printStackTrace();
    }
    return result;
  }

  /**
   * Find qualified server to bake provided vector and open channel to server
   *
   * @param request Send appropriate information to dispatcher, so it can decide which server is in
   *     charge
   * @throws ServerException Thrown when no suitable server is found to serve this request
   */
  public void getServer(GetServer request) throws ServerException {
    try {
      ServerInfo serverInfo = dispatcherServiceBlockingStub.getServer(request);
      if (serverInfo.getStatus().getCode() == Code.OK) {
        logger.info("[getServer] Is " + serverInfo.getAddress() + ":" + serverInfo.getPort());
        String server_address = serverInfo.getAddress() + ":" + serverInfo.getPort();
        server_channel =
            ManagedChannelBuilder.forTarget(server_address)
                // Channels are secure by default (via SSL/TLS). For the example we disable TLS to
                // avoid
                // needing certificates.
                .usePlaintext()
                .build();
        serverServiceBlockingStub = VectorServiceGrpc.newBlockingStub(server_channel);
        serverServiceStub = VectorServiceGrpc.newStub(server_channel);

      } else {
        logger.error("[getServer] Failed: {}", serverInfo.getStatus().getMessage());
        throw new ServerException(
            serverInfo.getStatus().getCode() + " " + serverInfo.getStatus().getMessage());
      }

    } catch (StatusRuntimeException e) {
      logger.error("RPC failed: {}", e.getStatus());
    }
  }

  /**
   * Since a connection to the dispatcher is established, this class is defined as AutoCloseable for
   * the sake of convenience. ManagedChannels use resources like threads and TCP connections. To
   * prevent leaking these resources, the channel should be shut down when it will no longer be
   * used. Also, if local garbage collection is enabled, clean local arrow storage
   */
  @Override
  public void close() throws Exception {
    final CountDownLatch finishLatch = new CountDownLatch(1);
    if (op.equals(OP_WRITE)) {
      // Get information from dispatcher
      GetServer serverRequest =
          GetServer.newBuilder()
              .setType(type_of_vector.getName())
              .setName(vectorName)
              .setOp(op)
              .setSize(fabricClient.allocator.getVector(vectorName, type_of_vector).getBufferSize())
              .setLength(
                  fabricClient.allocator.getVector(vectorName, type_of_vector).getValueCount())
              .build();
      getServer(serverRequest);

      // Stream vector to server
      StreamObserver<ServerStatus> responseObserver =
          new StreamObserver<>() {
            @Override
            public void onNext(ServerStatus response) {
              logger.info(
                  "[Close stream write] Code: "
                      + response.getCode()
                      + " Answer: "
                      + response.getMessage());
            }

            @Override
            public void onError(Throwable t) {
              logger.error("[Close stream write] RPC failed: {}", t.getMessage());
              t.printStackTrace();
              finishLatch.countDown();
            }

            @Override
            public void onCompleted() {
              logger.info("[Close stream write] Finished");
              finishLatch.countDown();
            }
          };
      StreamObserver<SetVector> requestObserver = serverServiceStub.setVector(responseObserver);
      ByteArrayInputStream vectorStream =
          fabricClient.allocator.getStream(
              (FieldVector) fabricClient.allocator.getVector(vectorName, type_of_vector));
      byte[] content;
      int written = 0;
      int estimated = vectorStream.available();
      logger.info("[Close stream write] Start upload " + Util.getSizeFromBytes(estimated));
      int grpcChunkSize = Integer.parseInt(Util.getConfig().getProperty("grpcChunkSize"));
      while ((content = vectorStream.readNBytes(grpcChunkSize)).length != 0) {
        written += content.length;
        try {
          requestObserver.onNext(
              SetVector.newBuilder()
                  .setName(vectorName)
                  .setType(type_of_vector.getName())
                  .setData(ByteString.copyFrom(content))
                  .build());
        } catch (StatusRuntimeException e) {
          logger.error("[Close stream write] RPC failed: {}", e.getStatus());
        }
      }
      assert written == estimated;
      logger.info("[Close stream write] Uploaded " + Util.getSizeFromBytes(written));
      requestObserver.onCompleted();
    } else {
      finishLatch.countDown();
    }
    if (localGC) {
      fabricClient.allocator.removeVector(vectorName, type_of_vector.getName());
    }
    // Receiving happens asynchronously
    if (finishLatch.await(1, TimeUnit.MINUTES)) {
      server_channel.shutdownNow().awaitTermination(1, TimeUnit.MINUTES);
      dispatcher_channel.shutdown().awaitTermination(1, TimeUnit.MINUTES);
    }
  }


  /**
   * If turned on, the vector will be deleted locally after handler is shut down. Especially handy,
   * to push data to servers and also save local ram.
   *
   * @return Handler configured to clean up after itself
   */
  public VectorHandler withLocalGC() {
    this.localGC = true;
    return this;
  }
}
