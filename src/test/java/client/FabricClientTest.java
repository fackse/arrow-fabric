package client;

import static org.example.ArrowFabric.OP.*;
import static org.junit.jupiter.api.Assertions.*;

import core.exceptions.ServerException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.apache.arrow.vector.Float8Vector;
import org.apache.arrow.vector.IntVector;
import org.apache.arrow.vector.VarCharVector;
import org.apache.arrow.vector.util.Text;
import org.junit.jupiter.api.*;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

class FabricClientTest {

  private static Random random;

  @BeforeAll
  static void beforeAll() {
    random = new Random();
  }

  @AfterAll
  static void afterAll() {
    JedisPool pool = new JedisPool(new JedisPoolConfig(), "localhost");
    try (Jedis jedis = pool.getResource()) {
      // jedis.flushAll();
    }
    pool.close();
  }

  @RepeatedTest(10)
  @DisplayName("Int vector creation, setting, getting and deletion from an server")
  void integer_test() throws Exception {
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

      try (VectorHandler vectorHandler = client.getHandler(IntVector.class, name, OP_READ)) {
        IntVector v3 = (IntVector) vectorHandler.getVector();
        for (int index : range) {
          assertEquals(v2.get(index), v3.get(index));
        }
      }
    }
  }

  @RepeatedTest(10)
  @DisplayName("Float vector creation, setting, getting and deletion from an server")
  void float_test() throws Exception {
    try (FabricClient client = new FabricClient()) {
      int start = 0;
      int end = 999;
      String name = UUID.randomUUID().toString().replace("-", "");
      List<Integer> range = IntStream.rangeClosed(start, end).boxed().collect(Collectors.toList());
      Float8Vector v2;
      try (VectorHandler vectorHandler = client.getHandler(Float8Vector.class, name, OP_WRITE)) {
        v2 = vectorHandler.createLocalVector();
        for (int index : range) {
          v2.set(index, random.nextFloat());
        }
        v2.setValueCount(end + 1);
      }

      try (VectorHandler vectorHandler = client.getHandler(Float8Vector.class, name, OP_READ)) {
        Float8Vector v3 = (Float8Vector) vectorHandler.getVector();
        for (int index : range) {
          assertEquals(v2.get(index), v3.get(index));
        }
      }
    }
  }

  @RepeatedTest(10)
  @DisplayName("Varchar vector creation, setting, getting and deletion from an server")
  void varchar_test() throws Exception {
    String name = UUID.randomUUID().toString().replace("-", "") + "varchar_test";
    int start = 0;
    int end = 999;
    List<Integer> range = IntStream.rangeClosed(start, end).boxed().collect(Collectors.toList());
    VarCharVector v2;
    try (FabricClient client = new FabricClient()) {
      try (VectorHandler vectorHandler = client.getHandler(VarCharVector.class, name, OP_WRITE)) {
        v2 = vectorHandler.createLocalVector();
        for (int index : range) {
          v2.setSafe(index, new Text(UUID.randomUUID().toString()));
        }
        v2.setValueCount(end + 1);
      }
    }
    try (FabricClient client = new FabricClient()) {
      try (VectorHandler vectorHandler = client.getHandler(VarCharVector.class, name, OP_READ)) {
        VarCharVector v3 = (VarCharVector) vectorHandler.getVector();
        for (int index : range) {
          assertEquals(Arrays.toString(v2.get(index)), Arrays.toString(v3.get(index)));
        }
      }
    }
  }

  @Test
  @DisplayName("Try to get a vector that does not exist")
  void vectorNotFound() throws Exception {
    String name = UUID.randomUUID().toString().replace("-", "") + "vectorNotFound";
    try (FabricClient client = new FabricClient()) {
      try (VectorHandler vectorHandler = client.getHandler(VarCharVector.class, name, OP_READ)) {
        vectorHandler.getVector();
      } catch (ServerException ignored) {
      }
    }
  }

  @Test
  @DisplayName("Try to set a vector that is already created")
  void vectorAlreadyCreated() throws Exception {
    try (FabricClient client = new FabricClient()) {
      String name = UUID.randomUUID().toString().replace("-", "") + "vectorAlreadyCreated";
      VarCharVector v2;
      try (VectorHandler vectorHandler = client.getHandler(VarCharVector.class, name, OP_WRITE)) {
        v2 = vectorHandler.createLocalVector();
        v2.set(0, new Text(UUID.randomUUID().toString()));
        v2.setValueCount(1);
      }
      try (VectorHandler vectorHandler = client.getHandler(VarCharVector.class, name, OP_WRITE)) {
        v2 = vectorHandler.createLocalVector();
        v2.set(0, new Text(UUID.randomUUID().toString()));
        v2.setValueCount(1);
      } catch (ServerException ignored) {
      }
    }
  }

  @Test
  @DisplayName("Set two vectors with same name, but different type")
  void vectorSameNameDifferentType() throws Exception {
    try (FabricClient client = new FabricClient()) {
      String name = UUID.randomUUID().toString().replace("-", "") + "vectorAlreadyCreated";
      VarCharVector v1;
      try (VectorHandler vectorHandler = client.getHandler(VarCharVector.class, name, OP_WRITE)) {
        v1 = vectorHandler.createLocalVector();
        v1.set(0, new Text(UUID.randomUUID().toString()));
        v1.setValueCount(1);
      }
      IntVector v2;
      try (VectorHandler vectorHandler = client.getHandler(IntVector.class, name, OP_WRITE)) {
        v2 = vectorHandler.createLocalVector();
        v2.set(0, 1);
        v2.setValueCount(1);
      }
      try (VectorHandler vectorHandler = client.getHandler(VarCharVector.class, name, OP_DELETE)) {
        assertTrue(vectorHandler.deleteVector());
      }
    }
  }

  @Test
  @DisplayName("Try to delete a vector that is already deleted")
  void vectorAlreadyDeleted() throws Exception {
    String name = UUID.randomUUID().toString().replace("-", "") + "vectorAlreadyDeleted";
    try (FabricClient client = new FabricClient()) {
      try (VectorHandler vectorHandler = client.getHandler(VarCharVector.class, name, OP_DELETE)) {
        vectorHandler.deleteVector();
      } catch (ServerException ignored) {
      }
    }
  }

  private Text buildText(int numbytes) {
    int written = 0;
    Text text = new Text();
    while (written < numbytes) {
      text.set("1");
      written += "1".getBytes(StandardCharsets.UTF_8).length;
    }
    return text;
  }

  @Test
  void bigVector() throws Exception {
    int size = 5 * 1024 * 1024; // vector is roughly 5Mb
    byte[] paylod = new byte[size];
    try (FabricClient client = new FabricClient()) {
      int start = 0;
      int end = 8388608; // vector is 8Mb
      String name = UUID.randomUUID().toString();
      List<Integer> range = IntStream.rangeClosed(start, end).boxed().collect(Collectors.toList());
      VarCharVector v2;
      try (VectorHandler vectorHandler = client.getHandler(VarCharVector.class, name, OP_WRITE)) {
        v2 = vectorHandler.createLocalVector();
        v2.setSafe(0, paylod);
        v2.setValueCount(1);
        assertEquals(size, v2.sizeOfValueBuffer());
      }

      try (VectorHandler vectorHandler = client.getHandler(VarCharVector.class, name, OP_READ)) {
        VarCharVector v3 = (VarCharVector) vectorHandler.getVector();
        assertEquals(size, v3.sizeOfValueBuffer());
      }
    }
  }
}
