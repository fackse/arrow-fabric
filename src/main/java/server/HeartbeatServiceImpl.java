package server;

import core.ArrowAllocator;
import io.grpc.stub.StreamObserver;
import org.example.ArrowFabric.*;

public class HeartbeatServiceImpl extends HeartbeatServiceGrpc.HeartbeatServiceImplBase {

  private final ArrowAllocator allocator;

  public HeartbeatServiceImpl(ArrowAllocator allocator) {
    this.allocator = allocator;
  }

  @Override
  public void ping(Empty request, StreamObserver<ServerStatus> responseObserver) {
    ServerInfo serverInfo =
        ServerInfo.newBuilder()
            .setLimit(allocator.rootAllocator.getLimit())
            .setAllocatedMemory(allocator.rootAllocator.getAllocatedMemory())
            .setHeadroom(allocator.rootAllocator.getHeadroom())
            .setNumVectors(allocator.numVectors())
            .build();
    ServerStatus reply =
        ServerStatus.newBuilder().setCode(Code.OK).setServerInfo(serverInfo).build();
    responseObserver.onNext(reply);
    responseObserver.onCompleted();
  }
}
