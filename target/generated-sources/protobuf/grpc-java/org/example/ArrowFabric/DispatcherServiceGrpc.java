package org.example.ArrowFabric;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.38.0)",
    comments = "Source: ArrowFabric.proto")
public final class DispatcherServiceGrpc {

  private DispatcherServiceGrpc() {}

  public static final String SERVICE_NAME = "org.example.ArrowFabric.DispatcherService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<org.example.ArrowFabric.GetServer,
      org.example.ArrowFabric.ServerInfo> getGetServerMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "getServer",
      requestType = org.example.ArrowFabric.GetServer.class,
      responseType = org.example.ArrowFabric.ServerInfo.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<org.example.ArrowFabric.GetServer,
      org.example.ArrowFabric.ServerInfo> getGetServerMethod() {
    io.grpc.MethodDescriptor<org.example.ArrowFabric.GetServer, org.example.ArrowFabric.ServerInfo> getGetServerMethod;
    if ((getGetServerMethod = DispatcherServiceGrpc.getGetServerMethod) == null) {
      synchronized (DispatcherServiceGrpc.class) {
        if ((getGetServerMethod = DispatcherServiceGrpc.getGetServerMethod) == null) {
          DispatcherServiceGrpc.getGetServerMethod = getGetServerMethod =
              io.grpc.MethodDescriptor.<org.example.ArrowFabric.GetServer, org.example.ArrowFabric.ServerInfo>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "getServer"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  org.example.ArrowFabric.GetServer.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  org.example.ArrowFabric.ServerInfo.getDefaultInstance()))
              .setSchemaDescriptor(new DispatcherServiceMethodDescriptorSupplier("getServer"))
              .build();
        }
      }
    }
    return getGetServerMethod;
  }

  private static volatile io.grpc.MethodDescriptor<org.example.ArrowFabric.ServerInfo,
      org.example.ArrowFabric.ServerStatus> getRegisterServerMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "registerServer",
      requestType = org.example.ArrowFabric.ServerInfo.class,
      responseType = org.example.ArrowFabric.ServerStatus.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<org.example.ArrowFabric.ServerInfo,
      org.example.ArrowFabric.ServerStatus> getRegisterServerMethod() {
    io.grpc.MethodDescriptor<org.example.ArrowFabric.ServerInfo, org.example.ArrowFabric.ServerStatus> getRegisterServerMethod;
    if ((getRegisterServerMethod = DispatcherServiceGrpc.getRegisterServerMethod) == null) {
      synchronized (DispatcherServiceGrpc.class) {
        if ((getRegisterServerMethod = DispatcherServiceGrpc.getRegisterServerMethod) == null) {
          DispatcherServiceGrpc.getRegisterServerMethod = getRegisterServerMethod =
              io.grpc.MethodDescriptor.<org.example.ArrowFabric.ServerInfo, org.example.ArrowFabric.ServerStatus>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "registerServer"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  org.example.ArrowFabric.ServerInfo.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  org.example.ArrowFabric.ServerStatus.getDefaultInstance()))
              .setSchemaDescriptor(new DispatcherServiceMethodDescriptorSupplier("registerServer"))
              .build();
        }
      }
    }
    return getRegisterServerMethod;
  }

  private static volatile io.grpc.MethodDescriptor<org.example.ArrowFabric.Empty,
      org.example.ArrowFabric.ServerInfo> getGetServersMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "getServers",
      requestType = org.example.ArrowFabric.Empty.class,
      responseType = org.example.ArrowFabric.ServerInfo.class,
      methodType = io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
  public static io.grpc.MethodDescriptor<org.example.ArrowFabric.Empty,
      org.example.ArrowFabric.ServerInfo> getGetServersMethod() {
    io.grpc.MethodDescriptor<org.example.ArrowFabric.Empty, org.example.ArrowFabric.ServerInfo> getGetServersMethod;
    if ((getGetServersMethod = DispatcherServiceGrpc.getGetServersMethod) == null) {
      synchronized (DispatcherServiceGrpc.class) {
        if ((getGetServersMethod = DispatcherServiceGrpc.getGetServersMethod) == null) {
          DispatcherServiceGrpc.getGetServersMethod = getGetServersMethod =
              io.grpc.MethodDescriptor.<org.example.ArrowFabric.Empty, org.example.ArrowFabric.ServerInfo>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "getServers"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  org.example.ArrowFabric.Empty.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  org.example.ArrowFabric.ServerInfo.getDefaultInstance()))
              .setSchemaDescriptor(new DispatcherServiceMethodDescriptorSupplier("getServers"))
              .build();
        }
      }
    }
    return getGetServersMethod;
  }

  private static volatile io.grpc.MethodDescriptor<org.example.ArrowFabric.Empty,
      org.example.ArrowFabric.VectorInfo> getGetVectorsMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "getVectors",
      requestType = org.example.ArrowFabric.Empty.class,
      responseType = org.example.ArrowFabric.VectorInfo.class,
      methodType = io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
  public static io.grpc.MethodDescriptor<org.example.ArrowFabric.Empty,
      org.example.ArrowFabric.VectorInfo> getGetVectorsMethod() {
    io.grpc.MethodDescriptor<org.example.ArrowFabric.Empty, org.example.ArrowFabric.VectorInfo> getGetVectorsMethod;
    if ((getGetVectorsMethod = DispatcherServiceGrpc.getGetVectorsMethod) == null) {
      synchronized (DispatcherServiceGrpc.class) {
        if ((getGetVectorsMethod = DispatcherServiceGrpc.getGetVectorsMethod) == null) {
          DispatcherServiceGrpc.getGetVectorsMethod = getGetVectorsMethod =
              io.grpc.MethodDescriptor.<org.example.ArrowFabric.Empty, org.example.ArrowFabric.VectorInfo>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "getVectors"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  org.example.ArrowFabric.Empty.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  org.example.ArrowFabric.VectorInfo.getDefaultInstance()))
              .setSchemaDescriptor(new DispatcherServiceMethodDescriptorSupplier("getVectors"))
              .build();
        }
      }
    }
    return getGetVectorsMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static DispatcherServiceStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<DispatcherServiceStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<DispatcherServiceStub>() {
        @java.lang.Override
        public DispatcherServiceStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new DispatcherServiceStub(channel, callOptions);
        }
      };
    return DispatcherServiceStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static DispatcherServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<DispatcherServiceBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<DispatcherServiceBlockingStub>() {
        @java.lang.Override
        public DispatcherServiceBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new DispatcherServiceBlockingStub(channel, callOptions);
        }
      };
    return DispatcherServiceBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static DispatcherServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<DispatcherServiceFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<DispatcherServiceFutureStub>() {
        @java.lang.Override
        public DispatcherServiceFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new DispatcherServiceFutureStub(channel, callOptions);
        }
      };
    return DispatcherServiceFutureStub.newStub(factory, channel);
  }

  /**
   */
  public static abstract class DispatcherServiceImplBase implements io.grpc.BindableService {

    /**
     */
    public void getServer(org.example.ArrowFabric.GetServer request,
        io.grpc.stub.StreamObserver<org.example.ArrowFabric.ServerInfo> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetServerMethod(), responseObserver);
    }

    /**
     */
    public void registerServer(org.example.ArrowFabric.ServerInfo request,
        io.grpc.stub.StreamObserver<org.example.ArrowFabric.ServerStatus> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getRegisterServerMethod(), responseObserver);
    }

    /**
     */
    public void getServers(org.example.ArrowFabric.Empty request,
        io.grpc.stub.StreamObserver<org.example.ArrowFabric.ServerInfo> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetServersMethod(), responseObserver);
    }

    /**
     */
    public void getVectors(org.example.ArrowFabric.Empty request,
        io.grpc.stub.StreamObserver<org.example.ArrowFabric.VectorInfo> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetVectorsMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getGetServerMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                org.example.ArrowFabric.GetServer,
                org.example.ArrowFabric.ServerInfo>(
                  this, METHODID_GET_SERVER)))
          .addMethod(
            getRegisterServerMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                org.example.ArrowFabric.ServerInfo,
                org.example.ArrowFabric.ServerStatus>(
                  this, METHODID_REGISTER_SERVER)))
          .addMethod(
            getGetServersMethod(),
            io.grpc.stub.ServerCalls.asyncServerStreamingCall(
              new MethodHandlers<
                org.example.ArrowFabric.Empty,
                org.example.ArrowFabric.ServerInfo>(
                  this, METHODID_GET_SERVERS)))
          .addMethod(
            getGetVectorsMethod(),
            io.grpc.stub.ServerCalls.asyncServerStreamingCall(
              new MethodHandlers<
                org.example.ArrowFabric.Empty,
                org.example.ArrowFabric.VectorInfo>(
                  this, METHODID_GET_VECTORS)))
          .build();
    }
  }

  /**
   */
  public static final class DispatcherServiceStub extends io.grpc.stub.AbstractAsyncStub<DispatcherServiceStub> {
    private DispatcherServiceStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected DispatcherServiceStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new DispatcherServiceStub(channel, callOptions);
    }

    /**
     */
    public void getServer(org.example.ArrowFabric.GetServer request,
        io.grpc.stub.StreamObserver<org.example.ArrowFabric.ServerInfo> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetServerMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void registerServer(org.example.ArrowFabric.ServerInfo request,
        io.grpc.stub.StreamObserver<org.example.ArrowFabric.ServerStatus> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getRegisterServerMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void getServers(org.example.ArrowFabric.Empty request,
        io.grpc.stub.StreamObserver<org.example.ArrowFabric.ServerInfo> responseObserver) {
      io.grpc.stub.ClientCalls.asyncServerStreamingCall(
          getChannel().newCall(getGetServersMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void getVectors(org.example.ArrowFabric.Empty request,
        io.grpc.stub.StreamObserver<org.example.ArrowFabric.VectorInfo> responseObserver) {
      io.grpc.stub.ClientCalls.asyncServerStreamingCall(
          getChannel().newCall(getGetVectorsMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class DispatcherServiceBlockingStub extends io.grpc.stub.AbstractBlockingStub<DispatcherServiceBlockingStub> {
    private DispatcherServiceBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected DispatcherServiceBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new DispatcherServiceBlockingStub(channel, callOptions);
    }

    /**
     */
    public org.example.ArrowFabric.ServerInfo getServer(org.example.ArrowFabric.GetServer request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetServerMethod(), getCallOptions(), request);
    }

    /**
     */
    public org.example.ArrowFabric.ServerStatus registerServer(org.example.ArrowFabric.ServerInfo request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getRegisterServerMethod(), getCallOptions(), request);
    }

    /**
     */
    public java.util.Iterator<org.example.ArrowFabric.ServerInfo> getServers(
        org.example.ArrowFabric.Empty request) {
      return io.grpc.stub.ClientCalls.blockingServerStreamingCall(
          getChannel(), getGetServersMethod(), getCallOptions(), request);
    }

    /**
     */
    public java.util.Iterator<org.example.ArrowFabric.VectorInfo> getVectors(
        org.example.ArrowFabric.Empty request) {
      return io.grpc.stub.ClientCalls.blockingServerStreamingCall(
          getChannel(), getGetVectorsMethod(), getCallOptions(), request);
    }
  }

  /**
   */
  public static final class DispatcherServiceFutureStub extends io.grpc.stub.AbstractFutureStub<DispatcherServiceFutureStub> {
    private DispatcherServiceFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected DispatcherServiceFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new DispatcherServiceFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<org.example.ArrowFabric.ServerInfo> getServer(
        org.example.ArrowFabric.GetServer request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetServerMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<org.example.ArrowFabric.ServerStatus> registerServer(
        org.example.ArrowFabric.ServerInfo request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getRegisterServerMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_GET_SERVER = 0;
  private static final int METHODID_REGISTER_SERVER = 1;
  private static final int METHODID_GET_SERVERS = 2;
  private static final int METHODID_GET_VECTORS = 3;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final DispatcherServiceImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(DispatcherServiceImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_GET_SERVER:
          serviceImpl.getServer((org.example.ArrowFabric.GetServer) request,
              (io.grpc.stub.StreamObserver<org.example.ArrowFabric.ServerInfo>) responseObserver);
          break;
        case METHODID_REGISTER_SERVER:
          serviceImpl.registerServer((org.example.ArrowFabric.ServerInfo) request,
              (io.grpc.stub.StreamObserver<org.example.ArrowFabric.ServerStatus>) responseObserver);
          break;
        case METHODID_GET_SERVERS:
          serviceImpl.getServers((org.example.ArrowFabric.Empty) request,
              (io.grpc.stub.StreamObserver<org.example.ArrowFabric.ServerInfo>) responseObserver);
          break;
        case METHODID_GET_VECTORS:
          serviceImpl.getVectors((org.example.ArrowFabric.Empty) request,
              (io.grpc.stub.StreamObserver<org.example.ArrowFabric.VectorInfo>) responseObserver);
          break;
        default:
          throw new AssertionError();
      }
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        default:
          throw new AssertionError();
      }
    }
  }

  private static abstract class DispatcherServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    DispatcherServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return org.example.ArrowFabric.ArrowFabric.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("DispatcherService");
    }
  }

  private static final class DispatcherServiceFileDescriptorSupplier
      extends DispatcherServiceBaseDescriptorSupplier {
    DispatcherServiceFileDescriptorSupplier() {}
  }

  private static final class DispatcherServiceMethodDescriptorSupplier
      extends DispatcherServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    DispatcherServiceMethodDescriptorSupplier(String methodName) {
      this.methodName = methodName;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.MethodDescriptor getMethodDescriptor() {
      return getServiceDescriptor().findMethodByName(methodName);
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (DispatcherServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new DispatcherServiceFileDescriptorSupplier())
              .addMethod(getGetServerMethod())
              .addMethod(getRegisterServerMethod())
              .addMethod(getGetServersMethod())
              .addMethod(getGetVectorsMethod())
              .build();
        }
      }
    }
    return result;
  }
}
