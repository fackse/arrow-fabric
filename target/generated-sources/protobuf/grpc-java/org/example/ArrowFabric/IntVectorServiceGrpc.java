package org.example.ArrowFabric;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.38.0)",
    comments = "Source: ArrowFabric.proto")
public final class IntVectorServiceGrpc {

  private IntVectorServiceGrpc() {}

  public static final String SERVICE_NAME = "org.example.ArrowFabric.IntVectorService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<org.example.ArrowFabric.CreateIntVector,
      org.example.ArrowFabric.Status> getCreateMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "create",
      requestType = org.example.ArrowFabric.CreateIntVector.class,
      responseType = org.example.ArrowFabric.Status.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<org.example.ArrowFabric.CreateIntVector,
      org.example.ArrowFabric.Status> getCreateMethod() {
    io.grpc.MethodDescriptor<org.example.ArrowFabric.CreateIntVector, org.example.ArrowFabric.Status> getCreateMethod;
    if ((getCreateMethod = IntVectorServiceGrpc.getCreateMethod) == null) {
      synchronized (IntVectorServiceGrpc.class) {
        if ((getCreateMethod = IntVectorServiceGrpc.getCreateMethod) == null) {
          IntVectorServiceGrpc.getCreateMethod = getCreateMethod =
              io.grpc.MethodDescriptor.<org.example.ArrowFabric.CreateIntVector, org.example.ArrowFabric.Status>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "create"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  org.example.ArrowFabric.CreateIntVector.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  org.example.ArrowFabric.Status.getDefaultInstance()))
              .setSchemaDescriptor(new IntVectorServiceMethodDescriptorSupplier("create"))
              .build();
        }
      }
    }
    return getCreateMethod;
  }

  private static volatile io.grpc.MethodDescriptor<org.example.ArrowFabric.SetIntVector,
      org.example.ArrowFabric.Status> getSetMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "set",
      requestType = org.example.ArrowFabric.SetIntVector.class,
      responseType = org.example.ArrowFabric.Status.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<org.example.ArrowFabric.SetIntVector,
      org.example.ArrowFabric.Status> getSetMethod() {
    io.grpc.MethodDescriptor<org.example.ArrowFabric.SetIntVector, org.example.ArrowFabric.Status> getSetMethod;
    if ((getSetMethod = IntVectorServiceGrpc.getSetMethod) == null) {
      synchronized (IntVectorServiceGrpc.class) {
        if ((getSetMethod = IntVectorServiceGrpc.getSetMethod) == null) {
          IntVectorServiceGrpc.getSetMethod = getSetMethod =
              io.grpc.MethodDescriptor.<org.example.ArrowFabric.SetIntVector, org.example.ArrowFabric.Status>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "set"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  org.example.ArrowFabric.SetIntVector.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  org.example.ArrowFabric.Status.getDefaultInstance()))
              .setSchemaDescriptor(new IntVectorServiceMethodDescriptorSupplier("set"))
              .build();
        }
      }
    }
    return getSetMethod;
  }

  private static volatile io.grpc.MethodDescriptor<org.example.ArrowFabric.GetIntVector,
      org.example.ArrowFabric.IntVector> getGetMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "get",
      requestType = org.example.ArrowFabric.GetIntVector.class,
      responseType = org.example.ArrowFabric.IntVector.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<org.example.ArrowFabric.GetIntVector,
      org.example.ArrowFabric.IntVector> getGetMethod() {
    io.grpc.MethodDescriptor<org.example.ArrowFabric.GetIntVector, org.example.ArrowFabric.IntVector> getGetMethod;
    if ((getGetMethod = IntVectorServiceGrpc.getGetMethod) == null) {
      synchronized (IntVectorServiceGrpc.class) {
        if ((getGetMethod = IntVectorServiceGrpc.getGetMethod) == null) {
          IntVectorServiceGrpc.getGetMethod = getGetMethod =
              io.grpc.MethodDescriptor.<org.example.ArrowFabric.GetIntVector, org.example.ArrowFabric.IntVector>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "get"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  org.example.ArrowFabric.GetIntVector.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  org.example.ArrowFabric.IntVector.getDefaultInstance()))
              .setSchemaDescriptor(new IntVectorServiceMethodDescriptorSupplier("get"))
              .build();
        }
      }
    }
    return getGetMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static IntVectorServiceStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<IntVectorServiceStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<IntVectorServiceStub>() {
        @java.lang.Override
        public IntVectorServiceStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new IntVectorServiceStub(channel, callOptions);
        }
      };
    return IntVectorServiceStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static IntVectorServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<IntVectorServiceBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<IntVectorServiceBlockingStub>() {
        @java.lang.Override
        public IntVectorServiceBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new IntVectorServiceBlockingStub(channel, callOptions);
        }
      };
    return IntVectorServiceBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static IntVectorServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<IntVectorServiceFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<IntVectorServiceFutureStub>() {
        @java.lang.Override
        public IntVectorServiceFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new IntVectorServiceFutureStub(channel, callOptions);
        }
      };
    return IntVectorServiceFutureStub.newStub(factory, channel);
  }

  /**
   */
  public static abstract class IntVectorServiceImplBase implements io.grpc.BindableService {

    /**
     */
    public void create(org.example.ArrowFabric.CreateIntVector request,
        io.grpc.stub.StreamObserver<org.example.ArrowFabric.Status> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getCreateMethod(), responseObserver);
    }

    /**
     */
    public void set(org.example.ArrowFabric.SetIntVector request,
        io.grpc.stub.StreamObserver<org.example.ArrowFabric.Status> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getSetMethod(), responseObserver);
    }

    /**
     */
    public void get(org.example.ArrowFabric.GetIntVector request,
        io.grpc.stub.StreamObserver<org.example.ArrowFabric.IntVector> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getCreateMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                org.example.ArrowFabric.CreateIntVector,
                org.example.ArrowFabric.Status>(
                  this, METHODID_CREATE)))
          .addMethod(
            getSetMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                org.example.ArrowFabric.SetIntVector,
                org.example.ArrowFabric.Status>(
                  this, METHODID_SET)))
          .addMethod(
            getGetMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                org.example.ArrowFabric.GetIntVector,
                org.example.ArrowFabric.IntVector>(
                  this, METHODID_GET)))
          .build();
    }
  }

  /**
   */
  public static final class IntVectorServiceStub extends io.grpc.stub.AbstractAsyncStub<IntVectorServiceStub> {
    private IntVectorServiceStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected IntVectorServiceStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new IntVectorServiceStub(channel, callOptions);
    }

    /**
     */
    public void create(org.example.ArrowFabric.CreateIntVector request,
        io.grpc.stub.StreamObserver<org.example.ArrowFabric.Status> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getCreateMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void set(org.example.ArrowFabric.SetIntVector request,
        io.grpc.stub.StreamObserver<org.example.ArrowFabric.Status> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getSetMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void get(org.example.ArrowFabric.GetIntVector request,
        io.grpc.stub.StreamObserver<org.example.ArrowFabric.IntVector> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class IntVectorServiceBlockingStub extends io.grpc.stub.AbstractBlockingStub<IntVectorServiceBlockingStub> {
    private IntVectorServiceBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected IntVectorServiceBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new IntVectorServiceBlockingStub(channel, callOptions);
    }

    /**
     */
    public org.example.ArrowFabric.Status create(org.example.ArrowFabric.CreateIntVector request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getCreateMethod(), getCallOptions(), request);
    }

    /**
     */
    public org.example.ArrowFabric.Status set(org.example.ArrowFabric.SetIntVector request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getSetMethod(), getCallOptions(), request);
    }

    /**
     */
    public org.example.ArrowFabric.IntVector get(org.example.ArrowFabric.GetIntVector request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetMethod(), getCallOptions(), request);
    }
  }

  /**
   */
  public static final class IntVectorServiceFutureStub extends io.grpc.stub.AbstractFutureStub<IntVectorServiceFutureStub> {
    private IntVectorServiceFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected IntVectorServiceFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new IntVectorServiceFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<org.example.ArrowFabric.Status> create(
        org.example.ArrowFabric.CreateIntVector request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getCreateMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<org.example.ArrowFabric.Status> set(
        org.example.ArrowFabric.SetIntVector request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getSetMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<org.example.ArrowFabric.IntVector> get(
        org.example.ArrowFabric.GetIntVector request) {
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
    private final IntVectorServiceImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(IntVectorServiceImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_CREATE:
          serviceImpl.create((org.example.ArrowFabric.CreateIntVector) request,
              (io.grpc.stub.StreamObserver<org.example.ArrowFabric.Status>) responseObserver);
          break;
        case METHODID_SET:
          serviceImpl.set((org.example.ArrowFabric.SetIntVector) request,
              (io.grpc.stub.StreamObserver<org.example.ArrowFabric.Status>) responseObserver);
          break;
        case METHODID_GET:
          serviceImpl.get((org.example.ArrowFabric.GetIntVector) request,
              (io.grpc.stub.StreamObserver<org.example.ArrowFabric.IntVector>) responseObserver);
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

  private static abstract class IntVectorServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    IntVectorServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return org.example.ArrowFabric.ArrowFabric.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("IntVectorService");
    }
  }

  private static final class IntVectorServiceFileDescriptorSupplier
      extends IntVectorServiceBaseDescriptorSupplier {
    IntVectorServiceFileDescriptorSupplier() {}
  }

  private static final class IntVectorServiceMethodDescriptorSupplier
      extends IntVectorServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    IntVectorServiceMethodDescriptorSupplier(String methodName) {
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
      synchronized (IntVectorServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new IntVectorServiceFileDescriptorSupplier())
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
