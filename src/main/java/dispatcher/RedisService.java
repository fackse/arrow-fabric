package dispatcher;

import static dispatcher.ServerNode.SERVER_SEPARATOR;

import com.google.gson.Gson;
import core.exceptions.VectorNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import org.example.ArrowFabric.VectorInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.ScanParams;
import redis.clients.jedis.ScanResult;
import redis.clients.jedis.exceptions.JedisConnectionException;

public class RedisService {

  /**
   * Logging instance
   */
  private static final Logger logger =
      LoggerFactory.getLogger(RedisService.class.getName());
  /**
   * {@link JedisPool} provided by {@link FabricDispatcher}
   */
  private final JedisPool jedisPool;
  /**
   * Used to transform classes into json and back
   */
  private final Gson gson = new Gson();
  /**
   * Divider used in redis to split Vector-type and -name and server address
   * E.g: org.apache.arrow.vector.IntVector/cb2652c323/localhost:50051
   */
  public static String KEY_SEPARATOR = "/";
  public static String REDIS_WILDCARD = "*";


  public RedisService(JedisPool jedisPool) {
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
   * Builds key for a vector
   *
   * @param name Name of the vector
   * @param type Type of the vector
   * @param node Server/Node where the vector will be saved
   * @return Key for Vector as {@link String}
   */
  private String buildKey(String name, String type, ServerNode node) {
    return type + KEY_SEPARATOR + name + KEY_SEPARATOR + node.toURI();
  }


  private String getKey(String name, String type) {
    return type + KEY_SEPARATOR + name + KEY_SEPARATOR + REDIS_WILDCARD;
  }

  /**
   * Add server/node information to redis. Also used for update. OPs are identical in redis since
   * there is no update.
   *
   * @param node Node to add
   */
  void addServerToRedis(ServerNode node) {
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
   * Removes server info including all its vectors from the redis instance.
   *
   * @param node Server which has to be removed from redis
   */
  void deleteServerFromRedis(ServerNode node) {
    ScanParams sp = new ScanParams();
    sp.match("*/" + node.toURI());
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
   * Adds vector information into redis.
   *
   * @param name        Name of the vector
   * @param type        Type of the vector
   * @param size        Size of vector in Bytes
   * @param numElements Number of elements inside the vector
   * @param node        Server/Node where the vector will be stored
   * @return True if everything went fine, otherwise false when vector with these parameters is
   * already present
   */
  protected boolean addVectorToRedis(
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

  protected HashMap<String, String> getServerFromVector(String name, String type) throws VectorNotFoundException {
    String key = getVectorFromRedis(name, type);
    HashMap<String, String> map = new HashMap<>();
    map.put(
        "address",
        key.split(KEY_SEPARATOR)[2].split("\\"+SERVER_SEPARATOR)[0]
    );
    map.put(
        "port",
        key.split(KEY_SEPARATOR)[2].split("\\"+SERVER_SEPARATOR)[1]
    );
    // org.apache.arrow.vector.IntVector/cbb53dbd2922466ca0ecdcef2652c323/localhost+SERVER_SEPARATOR+50051
    return map;
  }

  /**
   * Check if vector is already stored in redis.
   *
   * @param name Name of the vector
   * @param type Type of the vector
   * @return Returns server address and port
   */
  protected String getVectorFromRedis(String name, String type)
      throws VectorNotFoundException {
    String key = getKey(name, type);
    ScanParams sp = new ScanParams();
    sp.match(key);
    String cursor = "0";
    String result = null;
    try (Jedis jedis = jedisPool.getResource()) {
      do {
        ScanResult<String> ret = jedis.scan(cursor, sp);
        try {
          result = ret.getResult().get(0);
        } catch (IndexOutOfBoundsException ignored) {
        }
        cursor = ret.getCursor();
      } while (!cursor.equals("0"));

    }
    if (result != null) {
      return result;
    }
    throw new VectorNotFoundException();
  }

  /**
   * Delete vector information from redis
   *
   * @param name Name of the vector
   * @param type Type of the vector
   */
  void deleteVectorFromRedis(String name, String type) throws VectorNotFoundException {
    String key = getKey(name, type);
    try (Jedis jedis = jedisPool.getResource()) {
      jedis.del(
          getVectorFromRedis(name, type)
      );
    }
  }

  /**
   * Get all vector information from redis
   *
   * @return {@link ArrayList} containing {@link VectorInfo} for each vector found in redis
   */
  ArrayList<VectorInfo> getVectors() {
    ArrayList<VectorInfo> result = new ArrayList<>();
    try (Jedis jedis = jedisPool.getResource()) {
      String cursor = "0";
      ScanParams sp = new ScanParams();
      sp.match(REDIS_WILDCARD);
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


}
