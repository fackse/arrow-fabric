package client;

import static core.Util.getSizeFromBytes;
import static core.Util.printTable;

import core.ArrowAllocator;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.example.ArrowFabric.DispatcherServiceGrpc;
import org.example.ArrowFabric.Empty;
import org.example.ArrowFabric.OP;
import org.example.ArrowFabric.ServerInfo;
import org.example.ArrowFabric.VectorInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import picocli.CommandLine;
import picocli.CommandLine.Command;

@SuppressWarnings("FieldMayBeFinal")
/*
  The Fabric Client coordinates the allocation of vectors across different servers based on the
  {@link dispatcher.FabricDispatcher} instructions.
 */
@Command(
    name = "fabric-client",
    mixinStandardHelpOptions = true,
    description = "Starts the fabric client",
    version = "0.1")
public class FabricClient implements AutoCloseable {

  @CommandLine.Option(
      names = {"-p", "--dispatcher-port"},
      description = "Port from dispatcher (default: ${DEFAULT-VALUE})")
  private int dispatcherPort = 50052;

  @CommandLine.Option(
      names = {"-a", "--dispatcher-address"},
      description = "IP from dispatcher (default: ${DEFAULT-VALUE})")
  private String dispatcherAddress = "localhost";

  private static final Logger logger = LoggerFactory.getLogger(FabricClient.class.getName());
  private static final FabricClient INSTANCE = new FabricClient();

  /**
   * {@link org.example.ArrowFabric.DispatcherServiceGrpc.DispatcherServiceBlockingStub} to
   * communicate with the FabricDispatcher
   */
  private DispatcherServiceGrpc.DispatcherServiceBlockingStub dispatcherServiceBlockingStub;

  /**
   * {@link ManagedChannel} to talk with the FabricDispatcher
   */
  ManagedChannel dispatcher_channel;

  /**
   * This {@link ArrowAllocator} is in charge of the underlying Arrow storage
   */
  final ArrowAllocator allocator = new ArrowAllocator();

  /**
   * Initially, the address of the FabricDispatcher must be known. With the help of the {@link
   * dispatcher.FabricDispatcher}, the fabric client later decides on which server the vectors
   * should be stored.
   */
  private void buildChannel(){
    dispatcher_channel =
        ManagedChannelBuilder.forTarget(dispatcherAddress + ":" + dispatcherPort)
            .usePlaintext()
            .build();
    dispatcherServiceBlockingStub = DispatcherServiceGrpc.newBlockingStub(dispatcher_channel);
  }

  /**
   * For each vector a dedicated {@link VectorHandler} is used.
   *
   * @param type_of_vector Class of the vector
   * @param name           Name of the vector
   * @param op             Type of {@link OP} which is made on the vector
   * @return {@link VectorHandler} that enables working on a vector.
   */
  public VectorHandler getHandler(Class<?> type_of_vector, String name, OP op) {
    logger.info("[prepare] for " + type_of_vector.getSimpleName() + ":" + name + " with " + op);
    return new VectorHandler(this, dispatcherAddress, dispatcherPort, type_of_vector, name, op);
  }

  /**
   * Close channel to {@link dispatcher.FabricDispatcher}
   *
   * @throws Exception When awaitTermination messes up
   */
  @SuppressWarnings("FieldMayBeFinal")

  @Override
  public void close() throws Exception {
    dispatcher_channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
  }

  /**
   * List servers currently available
   */
  @SuppressWarnings("unused")
  @Command(name = "list-servers", description = "List Servers currently available")
  void commandListServers() {
    buildChannel();
    INSTANCE.listServers();
  }

  /**
   * Asks {@link dispatcher.FabricDispatcher} for active servers and prints table
   */
  private void listServers() {
    Empty request = Empty.newBuilder().build();
    Iterator<ServerInfo> serverInfoIterator;
    String[] headers = {
        "ID", "Address", "Port", "Allocated Memory", "Limit", "Headroom", "# Vectors"
    };
    ArrayList<ArrayList<String>> data = new ArrayList<>();
    try {
      serverInfoIterator = dispatcherServiceBlockingStub.getServers(request);

      for (Iterator<ServerInfo> it = serverInfoIterator; it.hasNext(); ) {
        ServerInfo info = it.next();
        String[] row = {
            String.valueOf(info.getId()),
            info.getAddress(),
            String.valueOf(info.getPort()),
            getSizeFromBytes(info.getAllocatedMemory()),
            getSizeFromBytes(info.getLimit()),
            getSizeFromBytes(info.getHeadroom()),
            String.valueOf(info.getNumVectors())
        };
        data.add(new ArrayList<>(List.of(row)));

      }
    } catch (StatusRuntimeException e) {
      logger.error("RPC failed: {}", e.getStatus());
    }
    printTable(headers, data);
  }

  /**
   * Asks {@link dispatcher.FabricDispatcher} for available vectors and prints table
   */
  private void getVectors() {
    String[] headers = {"Name", "Type", "Size", "# Elements", "Location / Server ID"};
    ArrayList<ArrayList<String>> data = new ArrayList<>();
    try {
      Iterator<VectorInfo> vectorInfoIterator =
          dispatcherServiceBlockingStub.getVectors(Empty.newBuilder().build());
      vectorInfoIterator.forEachRemaining(
          v ->
              data.add(
                  new ArrayList<>() {
                    {
                      add(v.getName());
                      add(v.getType());
                      add(getSizeFromBytes(v.getSize()));
                      add(String.valueOf(v.getNumElements()));
                      add(v.getLocation());

                    }
                  }));

    } catch (StatusRuntimeException e) {
      logger.error("RPC failed: {}", e.getStatus());
    }
    printTable(headers, data);
  }

  /**
   * List servers currently available
   */
  @SuppressWarnings("unused")
  @Command(name = "list-vectors", description = "List vectors currently allocated")
  void commandListVectors() {
    buildChannel();
    INSTANCE.getVectors();
  }

  public static void main(String[] args) throws Exception {
    int exitCode = new CommandLine(INSTANCE).execute(args);
    System.exit(exitCode);
  }
}
