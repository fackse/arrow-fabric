package dispatcher;

import java.util.Objects;
import org.ishugaliy.allgood.consistent.hash.node.Node;

@SuppressWarnings("all")
public class ServerNode implements Node {
  private final String address;
  private final int port;
  private long headroom;
  private long lastSeen;
  private long limit;
  private long allocatedMemory;
  private long numVectors;

  /** Representation of a server inside a hash ring. Used to allocate each vector to a server */
  public ServerNode(
      String address,
      int port,
      long lastSeen,
      long limit,
      long allocatedMemory,
      long numVectors,
      long headroom) {
    this.address = address;
    this.port = port;
    this.lastSeen = lastSeen;
    this.limit = limit;
    this.allocatedMemory = allocatedMemory;
    this.numVectors = numVectors;
    this.headroom = headroom;
  }

  /**
   * Factory method to create instance of the class.
   *
   * @param address - Address of the node
   * @param port - Port of the node
   * @param headroom Remaining free space in bytes
   * @return the instance of the Server
   */
  public static ServerNode of(
      String address,
      Integer port,
      long lastSeen,
      long limit,
      long allocatedMemory,
      long numVectors,
      long headroom) {
    return new ServerNode(address, port, lastSeen, limit, allocatedMemory, numVectors, headroom);
  }

  /** {@inheritDoc} */
  @Override
  public String getKey() {
    return address + ":" + port;
  }

  /** @return True if no space is left on the server, false if otherwise */
  public boolean isFull() {
    return limit - allocatedMemory <= 0;
  }

  /** {@inheritDoc} */
  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof ServerNode)) return false;
    ServerNode that = (ServerNode) o;
    return Objects.equals(address, that.address) && Objects.equals(port, that.port);
  }

  /** {@inheritDoc} */
  @Override
  public int hashCode() {
    return Objects.hash(address + ":" + port);
  }

  /** {@inheritDoc} */
  @Override
  public String toString() {
    return address + ":" + port;
  }

  /** @return Returns address from server*/
  public String getAddress() {
    return address;
  }
  /** @return Returns port from server*/
  public int getPort() {
    return port;
  }

  /** @return Returns URI of server. Used for redis.*/
  public String toURI() {
    return address + "+" + port;
  }

  /** @return Returns when the server was last seen as timestamp*/
  public long getLastSeen() {
    return lastSeen;
  }

  /** @return Returns remaining memory of server in bytes */
  public long getLimit() {
    return limit;
  }

  /** @return Returns already allocated memory by vectors on server in bytes*/
  public long getAllocatedMemory() {
    return allocatedMemory;
  }

  /** @return Returns number of vectors stored on this server*/
  public long getNumVectors() {
    return numVectors;
  }

  /** @return Returns remaining memory on server*/
  public long getHeadroom() {
    return headroom;
  }

  public ServerNode setLastSeen(long lastSeen) {
    this.lastSeen = lastSeen;
    return this;
  }

  public ServerNode setLimit(long limit) {
    this.limit = limit;
    return this;
  }

  public ServerNode setAllocatedMemory(long allocatedMemory) {
    this.allocatedMemory = allocatedMemory;
    return this;
  }

  public ServerNode setNumVectors(long numVectors) {
    this.numVectors = numVectors;
    return this;
  }

  public ServerNode setHeadroom(long headroom) {
    this.headroom = headroom;
    return this;
  }
}
