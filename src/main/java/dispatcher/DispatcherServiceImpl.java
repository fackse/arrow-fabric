package dispatcher;

import core.Util;
import core.exceptions.VectorNotFoundException;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;
import io.grpc.stub.StreamObserver;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import org.example.ArrowFabric.Code;
import org.example.ArrowFabric.DispatcherServiceGrpc;
import org.example.ArrowFabric.Empty;
import org.example.ArrowFabric.GetServer;
import org.example.ArrowFabric.HeartbeatServiceGrpc;
import org.example.ArrowFabric.ServerInfo;
import org.example.ArrowFabric.ServerStatus;
import org.example.ArrowFabric.VectorInfo;
import org.ishugaliy.allgood.consistent.hash.HashRing;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.exceptions.JedisConnectionException;

/**
 * Implements services provided by Grpc proto and also heartbeat monitoring of servers.
 */
public class DispatcherServiceImpl extends DispatcherServiceGrpc.DispatcherServiceImplBase
    implements Runnable {

  /**
   * Logging instance
   */
  private static final Logger logger =
      LoggerFactory.getLogger(DispatcherServiceImpl.class.getName());

  /**
   * {@inheritDoc}
   */
  private final HashRing<ServerNode> ring = HashRing.<ServerNode>newBuilder().build();

  private final RedisService redisService;


  public DispatcherServiceImpl(JedisPool jedisPool) throws JedisConnectionException {
    redisService = new RedisService(jedisPool);
  }

  private ServerNode selectNode(String name, String type, long sizeOfVector){
    return ring.locate(name + type, ring.size())
        .stream()
        .filter(n -> n.getHeadroom()>sizeOfVector)
        .findFirst().orElse(null);
  }


  /**
   * Select appropriate server for the vector received.
   *
   * @param request          Contains all important information of the vector
   * @param responseObserver Answer channel to the client which asks for this OP
   */
  @Override
  public void getServer(GetServer request, StreamObserver<ServerInfo> responseObserver) {
    String type = request.getType();
    String name = request.getName();

    ServerStatus status;
    ServerInfo reply = null;
    logger.info("[getTicket: " + request.getOp() + "] name " + name + " type " + type);
    switch (request.getOp()) {
      case OP_WRITE: // Client asks to write a vector
        long sizeOfVector = request.getSize();
        long numElements = request.getLength();
        ServerNode node = selectNode(name, type, sizeOfVector);
        if (node==null){ // In this case, the cluster is full
          status = ServerStatus.newBuilder().setCode(Code.ERROR_CLUSTER_MEMORY_EXHAUSTED).build();
          reply = ServerInfo.newBuilder().setStatus(status).build();
          logger.warn("[getTicket: " + request.getOp() + "] name " + name + " type " + type
              + " " + Code.ERROR_CLUSTER_MEMORY_EXHAUSTED);
          break; // Cluster is full, inform client
        }
        if (redisService.addVectorToRedis(name, type, sizeOfVector, numElements, node)) {
          status = ServerStatus.newBuilder().setCode(Code.OK).build();
          reply =
              ServerInfo.newBuilder()
                  .setAddress(node.getAddress())
                  .setPort(node.getPort())
                  .setStatus(status)
                  .build(); // All went fine, Send info to client
        } else {
          status =
              ServerStatus.newBuilder()
                  .setCode(Code.ERROR_VECTOR_ALREADY_EXISTENT)
                  .setMessage("Vector " + name + " already existent!!")
                  .build();
          reply =
              ServerInfo.newBuilder()
                  .setStatus(status)
                  .build(); // Inform client that vector is already present
        }
        break;
      case OP_READ: // Client wants to read a vector
        try {
          HashMap<String, String> nodeAddress = redisService.getServerFromVector(name, type);
          status = ServerStatus.newBuilder().setCode(Code.OK).build();
          reply =
              ServerInfo.newBuilder()
                  .setAddress(nodeAddress.get("address"))
                  .setPort(Integer.parseInt(nodeAddress.get("port")))
                  .setStatus(status)
                  .build(); // Server found where vector is stored, reply to client
        } catch (VectorNotFoundException e) {
          status =
              ServerStatus.newBuilder()
                  .setCode(Code.ERROR_VECTOR_NOT_FOUND)
                  .setMessage("Vector " + name + " not existent!!")
                  .build();
          reply =
              ServerInfo.newBuilder()
                  .setStatus(status)
                  .build(); // Server not found, inform client
        }
        break;
      case OP_DELETE: // Client wants to delete a vector
        try {
          HashMap<String, String> nodeAddress = redisService.getServerFromVector(name, type);
          redisService.deleteVectorFromRedis(name, type);
          status = ServerStatus.newBuilder().setCode(Code.OK).build();
          reply =
              ServerInfo.newBuilder()
                  .setAddress(nodeAddress.get("address"))
                  .setPort(Integer.parseInt(nodeAddress.get("port")))
                  .setStatus(status)
                  .build(); // Send server info to client

        } catch (VectorNotFoundException e) {
          status =
              ServerStatus.newBuilder()
                  .setCode(Code.ERROR_VECTOR_NOT_FOUND)
                  .setMessage("Vector " + name + " not existent!!")
                  .build();
          reply =
              ServerInfo.newBuilder()
                  .setStatus(status)
                  .build(); // Vector not found, inform client
        }
        break;
      case UNRECOGNIZED: // Should never be reached
        status =
            ServerStatus.newBuilder()
                .setCode(Code.UNRECOGNIZED)
                .setMessage("UNRECOGNIZED OP")
                .build();
        reply = ServerInfo.newBuilder().setStatus(status).build();
        break;
    }
    logger.info("[getTicket] Answer \n" + reply);
    responseObserver.onNext(reply);
    responseObserver.onCompleted();

  }

  /**
   * Called by every server to inform the dispatcher, that this server can now be used to store
   * vectors
   *
   * @param request          All information about the server, sent by the server
   * @param responseObserver Answer channel to the server
   */
  @Override
  public void registerServer(ServerInfo request, StreamObserver<ServerStatus> responseObserver) {
    ServerNode node =
        ServerNode.of(
            request.getAddress(),
            request.getPort(),
            System.currentTimeMillis() / 1000L,
            request.getLimit(),
            request.getAllocatedMemory(),
            request.getNumVectors(),
            request.getHeadroom());
    ring.add(node);
    redisService.addServerToRedis(node);
    logger.info("Server added. Servers now: " + ring.getNodes());
    ServerStatus reply = ServerStatus.newBuilder().setCode(Code.OK).build();
    responseObserver.onNext(reply);
    responseObserver.onCompleted();
  }

  /**
   * Returns all registered servers
   *
   * @param request          No data
   * @param responseObserver Answer channel to the client
   */
  @Override
  public void getServers(Empty request, StreamObserver<ServerInfo> responseObserver) {
    logger.info("[getServers]" + ring.getNodes());
    ring.getNodes().stream().map(node -> ServerInfo.newBuilder()
        .setPort(node.getPort())
        .setAddress(node.getAddress())
        .setId(node.hashCode())
        .setHeadroom(node.getHeadroom())
        .setAllocatedMemory(node.getAllocatedMemory())
        .setLimit(node.getLimit())
        .setNumVectors(node.getNumVectors())
        .build()).forEach(responseObserver::onNext);
    responseObserver.onCompleted();
  }

  /**
   * Returns all registered vectors
   *
   * @param request          No data
   * @param responseObserver Answer channel to the client
   */
  @Override
  public void getVectors(Empty request, StreamObserver<VectorInfo> responseObserver) {
    ArrayList<VectorInfo> vectors = redisService.getVectors();
    logger.info("[getVectors]" + vectors);
    vectors.forEach(responseObserver::onNext);
    responseObserver.onCompleted();
  }

  /**
   * Heartbeat thread which polls registered servers if they are still available
   */
  @Override
  public void run() {
    for (ServerNode node : ring.getNodes()) {
      ManagedChannel dispatcher_channel;
      dispatcher_channel =
          ManagedChannelBuilder.forTarget(node.getAddress() + ":" + node.getPort())
              .usePlaintext()
              .build();
      HeartbeatServiceGrpc.HeartbeatServiceBlockingStub heartbeatServiceBlockingStub =
          HeartbeatServiceGrpc.newBlockingStub(dispatcher_channel);
      // A random value is chosen within the timeout interval. This prevents broadcast storms in a
      // simple way
      int min = Integer.parseInt(Util.getConfig().getProperty("deadlineMsHeartbeatMin"));
      int max = Integer.parseInt(Util.getConfig().getProperty("deadlineMsHeartbeatMax"));
      int deadlineMs = ThreadLocalRandom.current().nextInt(min, max + 1);
      try {
        ServerStatus status =
            heartbeatServiceBlockingStub
                .withDeadlineAfter(deadlineMs, TimeUnit.MILLISECONDS)
                .ping(Empty.newBuilder().build());
        if (!status.getCode().equals(Code.OK)) {
          logger.error("[Heartbeat] Server " + node + " reported a problem. Reason " + status);
          redisService.deleteServerFromRedis(node);
          ring.remove(node);
        } else {
          logger.info("[Heartbeat] Server " + node + " reported on schedule");
          node.setAllocatedMemory(status.getServerInfo().getAllocatedMemory())
              .setLimit(status.getServerInfo().getLimit())
              .setNumVectors(status.getServerInfo().getNumVectors())
              .setLastSeen(System.currentTimeMillis() / 1000L)
              .setHeadroom(status.getServerInfo().getHeadroom());
          redisService.addServerToRedis(node);
        }
      } catch (StatusRuntimeException e) {
        logger.warn("[Heartbeat] Remove Server " + node + " Reason " + e);
        redisService.deleteServerFromRedis(node);
        ring.remove(node);
      }
      dispatcher_channel.shutdownNow();
    }
  }
}
