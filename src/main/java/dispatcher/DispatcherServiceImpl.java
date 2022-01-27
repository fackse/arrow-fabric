package dispatcher;

import com.google.gson.Gson;
import core.Util;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;
import io.grpc.stub.StreamObserver;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import org.example.ArrowFabric.*;
import org.ishugaliy.allgood.consistent.hash.HashRing;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.ScanParams;
import redis.clients.jedis.ScanResult;
import redis.clients.jedis.exceptions.JedisConnectionException;

/** Implements services provided by Grpc proto and also heartbeat monitoring of servers. */
public class DispatcherServiceImpl extends DispatcherServiceGrpc.DispatcherServiceImplBase
    implements Runnable {

  /** Logging instance */
  private static final Logger logger =
      LoggerFactory.getLogger(DispatcherServiceImpl.class.getName());

  /** {@inheritDoc} */
  private final HashRing<ServerNode> ring = HashRing.<ServerNode>newBuilder().build();

  /** {@link JedisPool} provided by {@link FabricDispatcher} */
  private final JedisPool jedisPool;

  /** Used to transform classes into json and back */
  private final Gson gson = new Gson();

  public DispatcherServiceImpl(JedisPool jedisPool) throws JedisConnectionException{
    this.jedisPool = jedisPool;
    try {
      this.jedisPool.getResource();
      logger.info("Connection to REDIS Server successful!");
    } catch (JedisConnectionException e) {
      logger.error("Cannot connect to REDIS Server!!!");
      e.printStackTrace();
      throw e;
    }

  }

  /**
   * Removes server info including all its vectors from the redis instance.
   *
   * @param node Server which has to be removed from redis
   */
  private void deleteServerFromRedis(ServerNode node) {
    ScanParams sp = new ScanParams();
    sp.match(node.toURI() + "/*");
    String cursor = "0";
    try (Jedis jedis = jedisPool.getResource()) {
      do {
        ScanResult<String> ret = jedis.scan(cursor, sp);
        List<String> result = ret.getResult();
        jedis.del(result.toArray(String[]::new));
        cursor = ret.getCursor();
      } while (!cursor.equals("0"));
      jedis.del(node.toURI());
    }
  }

  /**
   * Builds key for a vector
   *
   * @param name Name of the vector
   * @param type Type of the vector
   * @param node Server/Node where the vector will be saved
   * @return Key for Vector as {@link String}
   */
  private String buildKey(String name, String type, ServerNode node) {
    return String.format("%s/%s/%s", node.toURI(), type, name);
  }

  /**
   * Adds vector information into redis.
   *
   * @param name Name of the vector
   * @param type Type of the vector
   * @param size Size of vector in Bytes
   * @param numElements Number of elements inside the vector
   * @param node Server/Node where the vector will be stored
   * @return True if everything went fine, otherwise false when vector with these parameters is
   *     already present
   */
  private boolean addVectorToRedis(
      String name, String type, long size, long numElements, ServerNode node) {
    String key = buildKey(name, type, node);
    try (Jedis jedis = jedisPool.getResource()) {
      if (!jedis.exists(key)) {
        jedis.set(key, gson.toJson(new RedisVectorDataContainer(size, numElements)));
        return true;
      }
      return false;
    }
  }

  /**
   * Check if vector is already stored in redis.
   *
   * @param name Name of the vector
   * @param type Type of the vector
   * @param node Server/Node where the vector is stored
   * @return True if vector is present, false otherwise
   */
  private boolean getVectorFromRedis(String name, String type, ServerNode node) {
    String key = buildKey(name, type, node);
    try (Jedis jedis = jedisPool.getResource()) {
      return jedis.get(key) != null;
    }
  }

  /**
   * Delete vector information from redis
   *
   * @param name Name of the vector
   * @param type Type of the vector
   * @param node Server/Node where the vector is stored
   * @return True if vector was found and is removed, false if key was not found
   */
  private boolean deleteVectorFromRedis(String name, String type, ServerNode node) {
    String key = buildKey(name, type, node);
    try (Jedis jedis = jedisPool.getResource()) {
      return jedis.del(key) > 0;
    }
  }

  /**
   * Get all vector information from redis
   *
   * @return {@link ArrayList} containing {@link VectorInfo} for each vector found in redis
   */
  private ArrayList<VectorInfo> getVectors() {
    ArrayList<VectorInfo> result = new ArrayList<>();
    try (Jedis jedis = jedisPool.getResource()) {
      String cursor = "0";
      ScanParams sp = new ScanParams();
      sp.match("*");
      do {
        ScanResult<String> scanResult = jedis.scan(cursor, sp);
        for (String key : scanResult.getResult()) {
          List<String> info = new ArrayList<>(List.of(key.split("/")));
          if (info.size() == 3) {
            // info: (0)=nodeIp:nodePort (1)=VectorClass (2)=VectorName
            info.set(0, info.get(0).replace("+", ":"));
            info.add(jedis.get(key));
            RedisVectorDataContainer redisData =
                gson.fromJson(jedis.get(key), RedisVectorDataContainer.class);
            result.add(
                VectorInfo.newBuilder()
                    .setName(info.get(2))
                    .setSize(redisData.getSize())
                    .setNumElements(redisData.getNumElements())
                    .setType(info.get(1))
                    .setLocation(
                        String.valueOf(Objects.hash(info.get(0)))) // use hashed value as node id
                    .build());
          }
        }
        cursor = scanResult.getCursor();
      } while (!"0".equals(cursor));
    }
    return result;
  }

  /**
   * Add server/node information to redis. Also used for update. OPs are identical in redis since
   * there is no update.
   *
   * @param node Node to add
   */
  private void addServerToRedis(ServerNode node) {
    String key = node.toURI();
    try (Jedis jedis = jedisPool.getResource()) {
      jedis.set(
          key,
          gson.toJson(
              new RedisServerDataContainer(
                  node.getAddress(),
                  node.getPort(),
                  node.getLastSeen(),
                  node.getLimit(),
                  node.getAllocatedMemory(),
                  node.getNumVectors())));
    }
  }

  /**
   * Select appropriate server for the vector received.
   *
   * @param request Contains all important information of the vector
   * @param responseObserver Answer channel to the client which asks for this OP
   */
  @Override
  public void getServer(GetServer request, StreamObserver<ServerInfo> responseObserver) {
    String type = request.getType();
    String name = request.getName();

    // Find suitable server for the vector according its name and type
    Optional<ServerNode> node = ring.locate(name + type);
    if (node.isPresent()) {
      ServerStatus status;
      ServerInfo reply = null;
      logger.info("[getTicket: " + request.getOp() + "] name " + name + " type " + type);
      switch (request.getOp()) {
        case OP_WRITE: // Client asks to write a vector
          long sizeOfVector = request.getSize();
          long numElements = request.getLength();
          if (node.get().getHeadroom() < sizeOfVector) {
            status = ServerStatus.newBuilder().setCode(Code.ERROR_SERVER_MEMORY_EXHAUSTED).build();
            reply = ServerInfo.newBuilder().setStatus(status).build();
            break; // Server is full, inform client
          }
          if (addVectorToRedis(name, type, sizeOfVector, numElements, node.get())) {
            status = ServerStatus.newBuilder().setCode(Code.OK).build();
            reply =
                ServerInfo.newBuilder()
                    .setAddress(node.get().getAddress())
                    .setPort(node.get().getPort())
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
          if (getVectorFromRedis(name, type, node.get())) {
            status = ServerStatus.newBuilder().setCode(Code.OK).build();
            reply =
                ServerInfo.newBuilder()
                    .setAddress(node.get().getAddress())
                    .setPort(node.get().getPort())
                    .setStatus(status)
                    .build(); // Server found where vector is stored, reply to client
          } else {
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
          if (deleteVectorFromRedis(name, type, node.get())) {
            status = ServerStatus.newBuilder().setCode(Code.OK).build();
            reply =
                ServerInfo.newBuilder()
                    .setAddress(node.get().getAddress())
                    .setPort(node.get().getPort())
                    .setStatus(status)
                    .build(); // Send server info to client
          } else {
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
      logger.info("[getTicket] Answer " + reply);
      responseObserver.onNext(reply);
      responseObserver.onCompleted();
    }
  }

  /**
   * Called by every server to inform the dispatcher, that this server can now be used to store
   * vectors
   *
   * @param request All information about the server, sent by the server
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
    addServerToRedis(node);
    logger.info("Server added. Servers now: " + ring.getNodes());
    ServerStatus reply = ServerStatus.newBuilder().setCode(Code.OK).build();
    responseObserver.onNext(reply);
    responseObserver.onCompleted();
  }

  /**
   * Returns all registered servers
   *
   * @param request No data
   * @param responseObserver Answer channel to the client
   */
  @Override
  public void getServers(Empty request, StreamObserver<ServerInfo> responseObserver) {
    for (ServerNode node : ring.getNodes()) {
      responseObserver.onNext(
          ServerInfo.newBuilder()
              .setPort(node.getPort())
              .setAddress(node.getAddress())
              .setId(node.hashCode())
              .setHeadroom(node.getHeadroom())
              .setAllocatedMemory(node.getAllocatedMemory())
              .setLimit(node.getLimit())
              .setNumVectors(node.getNumVectors())
              .build());
    }
    responseObserver.onCompleted();
  }

  /**
   * Returns all registered vectors
   *
   * @param request No data
   * @param responseObserver Answer channel to the client
   */
  @Override
  public void getVectors(Empty request, StreamObserver<VectorInfo> responseObserver) {
    ArrayList<VectorInfo> vectors = getVectors();
    logger.info("[getVectors]" + vectors);
    vectors.forEach(responseObserver::onNext);
    responseObserver.onCompleted();
  }

  /** Heartbeat thread which polls registered servers if they are still available */
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
          deleteServerFromRedis(node);
          ring.remove(node);
        } else {
          logger.info("[Heartbeat] Server " + node + " reported on schedule");
          node.setAllocatedMemory(status.getServerInfo().getAllocatedMemory())
              .setLimit(status.getServerInfo().getLimit())
              .setNumVectors(status.getServerInfo().getNumVectors())
              .setLastSeen(System.currentTimeMillis() / 1000L)
              .setHeadroom(status.getServerInfo().getHeadroom());
          addServerToRedis(node);
        }
      } catch (StatusRuntimeException e) {
        logger.warn("[Heartbeat] Remove Server " + node + " Reason " + e);
        deleteServerFromRedis(node);
        ring.remove(node);
      }
      dispatcher_channel.shutdownNow();
    }
  }
}
