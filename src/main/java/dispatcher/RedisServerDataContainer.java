package dispatcher;

/**
 * Data container which holds information about a server. This class will later be serialized into a
 * json object
 */
@SuppressWarnings("all")
public class RedisServerDataContainer {
  /** Address of the server (like: 127.0.0.1 or localhost) */
  private String address;

  /** Port used by the server */
  private int port;

  /** Timestamp when the server was last seen by the dispatcher */
  private long lastSeen;

  /** Memory limit of the server in bytes */
  private long limit;

  /** Memory already allocated by vectors in bytes */
  private long allocatedMemory;

  /** Number of vector stored by this server */
  private long numVectors;

  /** Need for serialisation */
  RedisServerDataContainer() {
    // no-args constructor
  }

  public RedisServerDataContainer(
      String address, int port, long lastSeen, long limit, long allocatedMemory, long numVectors) {
    this.address = address;
    this.port = port;
    this.lastSeen = lastSeen;
    this.limit = limit;
    this.allocatedMemory = allocatedMemory;
    this.numVectors = numVectors;
  }

  /** {@inheritDoc} */
  @Override
  public String toString() {
    return "RedisServerDataContainer{"
        + "address='"
        + address
        + '\''
        + ", port="
        + port
        + ", lastSeen="
        + lastSeen
        + ", limit="
        + limit
        + ", allocatedMemory="
        + allocatedMemory
        + ", numVectors="
        + numVectors
        + '}';
  }
}
