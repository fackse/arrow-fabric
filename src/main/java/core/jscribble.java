package core;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class jscribble {

  public static void main(String[] args) throws Exception {
    JedisPool pool = new JedisPool("localhost", 6379);
    try (Jedis jedis = pool.getResource()) {
      jedis.set("clientName", "Jedis");
    }
  }
}
