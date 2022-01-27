package dispatcher;

/**
 * Data container which holds information about a vector. This class will later be serialized into a
 * json object
 */
@SuppressWarnings("all")
public class RedisVectorDataContainer {
  /** Size of the vector in bytes */
  private long size;

  /** Number of elements inside the vector */
  private long numElements;
  /** Need for serialisation */
  RedisVectorDataContainer() {
    // no-args constructor
  }

  RedisVectorDataContainer(long size, long numElements) {
    this.size = size;
    this.numElements = numElements;
  }

  /** {@inheritDoc} */
  @Override
  public String toString() {
    return "RedisVectorDataContainer{" + "size=" + size + ", numElements=" + numElements + '}';
  }

  /** @return Returns size of the vector in bytes */
  public long getSize() {
    return size;
  }

  /** @return Returns number of elements inside the vector */
  public long getNumElements() {
    return numElements;
  }
}
