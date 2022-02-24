package core;

import static org.example.ArrowFabric.OP.OP_READ;
import static org.example.ArrowFabric.OP.OP_WRITE;

import client.FabricClient;
import client.VectorHandler;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.apache.arrow.vector.IntVector;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class jscribble {

  public static void main(String[] args) throws Exception {
    Random random = new Random();
    try (FabricClient client = new FabricClient()) {
      int start = 0;
      int end = 999;
      String name = UUID.randomUUID().toString().replace("-", "");
      List<Integer> range = IntStream.rangeClosed(start, end).boxed().collect(Collectors.toList());
      IntVector v2;
      try (VectorHandler vectorHandler = client.getHandler(IntVector.class, name, OP_WRITE)) {
        v2 = vectorHandler.createLocalVector();
        for (int index : range) {
          v2.set(index, random.nextInt(end - start) + start);
        }
        v2.setValueCount(end + 1);
      }
    }
  }
}
