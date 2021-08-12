package org.example.ArrowFabric;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.38.0)",
    comments = "Source: ArrowFabric.proto")
public final class VectorServiceGrpc {

  private VectorServiceGrpc() {}

  public static final String SERVICE_NAME = "org.example.ArrowFabric.VectorService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<org.example.ArrowFabric.CreateVector,
      org.example.ArrowFabric.Status> getCreateMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "create",
      requestType = org.example.ArrowFabric.CreateVector.class,
      responseType = org.example.ArrowFabric.Status.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<org.example.ArrowFabric.CreateVector,
      org.example.ArrowFabric.Status> getCreateMethod() {
    io.grpc.MethodDescriptor<org.example.ArrowFabric.CreateVector, org.example.ArrowFabric.Status> getCreateMethod;
    if ((getCreateMethod = VectorServiceGrpc.getCreateMethod) == null) {
      synchronized (VectorServiceGrpc.class) {
        if ((getCreateMethod = VectorServiceGrpc.getCreateMethod) == null) {
          VectorServiceGrpc.getCreateMethod = getCreateMethod =
              io.grpc.MethodDescriptor.<org.example.ArrowFabric.CreateVector, org.example.ArrowFabric.Status>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "create"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  org.example.ArrowFabric.CreateVector.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  org.example.ArrowFabric.Status.getDefaultInstance()))
              .setSchemaDescriptor(new VectorServiceMethodDescriptorSupplier("create"))
              .build();
        }
      }
    }
    return getCreateMethod;
  }

  private static volatile io.grpc.MethodDescriptor<org.example.ArrowFabric.SetVector,
      org.example.ArrowFabric.Status> getSetMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "set",
      requestType = org.example.ArrowFabric.SetVector.class,
      responseType = org.example.ArrowFabric.Status.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<org.example.ArrowFabric.SetVector,
      org.example.ArrowFabric.Status> getSetMethod() {
    io.grpc.MethodDescriptor<org.example.ArrowFabric.SetVector, org.example.ArrowFabric.Status> getSetMethod;
    if ((getSetMethod = VectorServiceGrpc.getSetMethod) == null) {
      synchronized (VectorServiceGrpc.class) {
        if ((getSetMethod = VectorServiceGrpc.getSetMethod) == null) {
          VectorServiceGrpc.getSetMethod = getSetMethod =
              io.grpc.MethodDescriptor.<org.example.ArrowFabric.SetVector, org.example.ArrowFabric.Status>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "set"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  org.example.ArrowFabric.SetVector.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  org.example.ArrowFabric.Status.getDefaultInstance()))
              .setSchemaDescriptor(new VectorServiceMethodDescriptorSupplier("set"))
              .build();
        }
      }
    }
    return getSetMethod;
  }

  private static volatile io.grpc.MethodDescriptor<org.example.ArrowFabric.GetVector,
      org.example.ArrowFabric.Vector> getGetMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "get",
      requestType = org.example.ArrowFabric.GetVector.class,
      responseType = org.example.ArrowFabric.Vector.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<org.example.ArrowFabric.GetVector,
      org.example.ArrowFabric.Vector> getGetMethod() {
    io.grpc.MethodDescriptor<org.example.ArrowFabric.GetVector, org.example.ArrowFabric.Vector> getGetMethod;
    if ((getGetMethod = VectorServiceGrpc.getGetMethod) == null) {
      synchronized (VectorServiceGrpc.class) {
        if ((getGetMethod = VectorServiceGrpc.getGetMethod) == null) {
          VectorServiceGrpc.getGetMethod = getGetMethod =
              io.grpc.MethodDescriptor.<org.example.ArrowFabric.GetVector, org.example.ArrowFabric.Vector>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "get"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  org.example.ArrowFabric.GetVector.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  org.example.ArrowFabric.Vector.getDefaultInstance()))
              .setSchemaDescriptor(new VectorServiceMethodDescriptorSupplier("get"))
              .build();
        }
      }
    }
    return getGetMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static VectorServiceStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<VectorServiceStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<VectorServiceStub>() {
        @java.lang.Override
        public VectorServiceStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new VectorServiceStub(channel, callOptions);
        }
      };
    return VectorServiceStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static VectorServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<VectorServiceBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<VectorServiceBlockingStub>() {
        @java.lang.Override
        public VectorServiceBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new VectorServiceBlockingStub(channel, callOptions);
        }
      };
    return VectorServiceBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static VectorServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<VectorServiceFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<VectorServiceFutureStub>() {
        @java.lang.Override
        public VectorServiceFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new VectorServiceFutureStub(channel, callOptions);
        }
      };
    return VectorServiceFutureStub.newStub(factory, channel);
  }

  /**
   */
  public static abstract class VectorServiceImplBase implements io.grpc.BindableService {

    /**
     */
    public void create(org.example.ArrowFabric.CreateVector request,
        io.grpc.stub.StreamObserver<org.example.ArrowFabric.Status> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getCreateMethod(), responseObserver);
    }

    /**
     */
    public void set(org.example.ArrowFabric.SetVector request,
        io.grpc.stub.StreamObserver<org.example.ArrowFabric.Status> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getSetMethod(), responseObserver);
    }

    /**
     */
    public void get(org.example.ArrowFabric.GetVector request,
        io.grpc.stub.StreamObserver<org.example.ArrowFabric.Vector> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getCreateMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                org.example.ArrowFabric.CreateVector,
                org.example.ArrowFabric.Status>(
                  this, METHODID_CREATE)))
          .addMethod(
            getSetMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                org.example.ArrowFabric.SetVector,
                org.example.ArrowFabric.Status>(
                  this, METHODID_SET)))
          .addMethod(
            getGetMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                org.example.ArrowFabric.GetVector,
                org.example.ArrowFabric.Vector>(
                  this, METHODID_GET)))
          .build();
    }
  }

  /**
   */
  public static final class VectorServiceStub extends io.grpc.stub.AbstractAsyncStub<VectorServiceStub> {
    private VectorServiceStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected VectorServiceStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new VectorServiceStub(channel, callOptions);
    }

    /**
     */
    public void create(org.example.ArrowFabric.CreateVector request,
        io.grpc.stub.StreamObserver<org.example.ArrowFabric.Status> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getCreateMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void set(org.example.ArrowFabric.SetVector request,
        io.grpc.stub.StreamObserver<org.example.ArrowFabric.Status> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getSetMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void get(org.example.ArrowFabric.GetVector request,
        io.grpc.stub.StreamObserver<org.example.ArrowFabric.Vector> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class VectorServiceBlockingStub extends io.grpc.stub.AbstractBlockingStub<VectorServiceBlockingStub> {
    private VectorServiceBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected VectorServiceBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new VectorServiceBlockingStub(channel, callOptions);
    }

    /**
     */
    public org.example.ArrowFabric.Status create(org.example.ArrowFabric.CreateVector request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getCreateMethod(), getCallOptions(), request);
    }

    /**
     */
    public org.example.ArrowFabric.Status set(org.example.ArrowFabric.SetVector request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getSetMethod(), getCallOptions(), request);
    }

    /**
     */
    public org.example.ArrowFabric.Vector get(org.example.ArrowFabric.GetVector request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetMethod(), getCallOptions(), request);
    }
  }

  /**
   */
  public static final class VectorServiceFutureStub extends io.grpc.stub.AbstractFutureStub<VectorServiceFutureStub> {
    private VectorServiceFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected VectorServiceFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new VectorServiceFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<org.example.ArrowFabric.Status> create(
        org.example.ArrowFabric.CreateVector request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getCreateMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<org.example.ArrowFabric.Status> set(
        org.example.ArrowFabric.SetVector request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getSetMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<org.example.ArrowFabric.Vector> get(
        org.example.ArrowFabric.GetVector request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_CREATE = 0;
  private static final int METHODID_SET = 1;
  private static final int METHODID_GET = 2;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final VectorServiceImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(VectorServiceImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_CREATE:
          serviceImpl.create((org.example.ArrowFabric.CreateVector) request,
              (io.grpc.stub.StreamObserver<org.example.ArrowFabric.Status>) responseObserver);
          break;
        case METHODID_SET:
          serviceImpl.set((org.example.ArrowFabric.SetVector) request,
              (io.grpc.stub.StreamObserver<org.example.ArrowFabric.Status>) responseObserver);
          break;
        case METHODID_GET:
          serviceImpl.get((org.example.ArrowFabric.GetVector) request,
              (io.grpc.stub.StreamObserver<org.example.ArrowFabric.Vector>) responseObserver);
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

  private static abstract class VectorServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    VectorServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return org.example.ArrowFabric.ArrowFabric.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("VectorService");
    }
  }

  private static final class VectorServiceFileDescriptorSupplier
      extends VectorServiceBaseDescriptorSupplier {
    VectorServiceFileDescriptorSupplier() {}
  }

  private static final class VectorServiceMethodDescriptorSupplier
      extends VectorServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    VectorServiceMethodDescriptorSupplier(String methodName) {
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
      synchronized (VectorServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new VectorServiceFileDescriptorSupplier())
              .addMethod(getCreateMethod())
              .addMethod(getSetMethod())
              .addMethod(getGetMethod())
              .build();
        }
      }
    }
    return result;
  }
}
