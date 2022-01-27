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
      org.example.ArrowFabric.ServerStatus> getCreateVectorMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "createVector",
      requestType = org.example.ArrowFabric.CreateVector.class,
      responseType = org.example.ArrowFabric.ServerStatus.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<org.example.ArrowFabric.CreateVector,
      org.example.ArrowFabric.ServerStatus> getCreateVectorMethod() {
    io.grpc.MethodDescriptor<org.example.ArrowFabric.CreateVector, org.example.ArrowFabric.ServerStatus> getCreateVectorMethod;
    if ((getCreateVectorMethod = VectorServiceGrpc.getCreateVectorMethod) == null) {
      synchronized (VectorServiceGrpc.class) {
        if ((getCreateVectorMethod = VectorServiceGrpc.getCreateVectorMethod) == null) {
          VectorServiceGrpc.getCreateVectorMethod = getCreateVectorMethod =
              io.grpc.MethodDescriptor.<org.example.ArrowFabric.CreateVector, org.example.ArrowFabric.ServerStatus>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "createVector"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  org.example.ArrowFabric.CreateVector.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  org.example.ArrowFabric.ServerStatus.getDefaultInstance()))
              .setSchemaDescriptor(new VectorServiceMethodDescriptorSupplier("createVector"))
              .build();
        }
      }
    }
    return getCreateVectorMethod;
  }

  private static volatile io.grpc.MethodDescriptor<org.example.ArrowFabric.SetVector,
      org.example.ArrowFabric.ServerStatus> getSetVectorMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "setVector",
      requestType = org.example.ArrowFabric.SetVector.class,
      responseType = org.example.ArrowFabric.ServerStatus.class,
      methodType = io.grpc.MethodDescriptor.MethodType.CLIENT_STREAMING)
  public static io.grpc.MethodDescriptor<org.example.ArrowFabric.SetVector,
      org.example.ArrowFabric.ServerStatus> getSetVectorMethod() {
    io.grpc.MethodDescriptor<org.example.ArrowFabric.SetVector, org.example.ArrowFabric.ServerStatus> getSetVectorMethod;
    if ((getSetVectorMethod = VectorServiceGrpc.getSetVectorMethod) == null) {
      synchronized (VectorServiceGrpc.class) {
        if ((getSetVectorMethod = VectorServiceGrpc.getSetVectorMethod) == null) {
          VectorServiceGrpc.getSetVectorMethod = getSetVectorMethod =
              io.grpc.MethodDescriptor.<org.example.ArrowFabric.SetVector, org.example.ArrowFabric.ServerStatus>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.CLIENT_STREAMING)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "setVector"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  org.example.ArrowFabric.SetVector.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  org.example.ArrowFabric.ServerStatus.getDefaultInstance()))
              .setSchemaDescriptor(new VectorServiceMethodDescriptorSupplier("setVector"))
              .build();
        }
      }
    }
    return getSetVectorMethod;
  }

  private static volatile io.grpc.MethodDescriptor<org.example.ArrowFabric.GetVector,
      org.example.ArrowFabric.SetVector> getGetVectorMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "getVector",
      requestType = org.example.ArrowFabric.GetVector.class,
      responseType = org.example.ArrowFabric.SetVector.class,
      methodType = io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
  public static io.grpc.MethodDescriptor<org.example.ArrowFabric.GetVector,
      org.example.ArrowFabric.SetVector> getGetVectorMethod() {
    io.grpc.MethodDescriptor<org.example.ArrowFabric.GetVector, org.example.ArrowFabric.SetVector> getGetVectorMethod;
    if ((getGetVectorMethod = VectorServiceGrpc.getGetVectorMethod) == null) {
      synchronized (VectorServiceGrpc.class) {
        if ((getGetVectorMethod = VectorServiceGrpc.getGetVectorMethod) == null) {
          VectorServiceGrpc.getGetVectorMethod = getGetVectorMethod =
              io.grpc.MethodDescriptor.<org.example.ArrowFabric.GetVector, org.example.ArrowFabric.SetVector>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "getVector"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  org.example.ArrowFabric.GetVector.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  org.example.ArrowFabric.SetVector.getDefaultInstance()))
              .setSchemaDescriptor(new VectorServiceMethodDescriptorSupplier("getVector"))
              .build();
        }
      }
    }
    return getGetVectorMethod;
  }

  private static volatile io.grpc.MethodDescriptor<org.example.ArrowFabric.GetVector,
      org.example.ArrowFabric.ServerStatus> getDeleteVectorMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "deleteVector",
      requestType = org.example.ArrowFabric.GetVector.class,
      responseType = org.example.ArrowFabric.ServerStatus.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<org.example.ArrowFabric.GetVector,
      org.example.ArrowFabric.ServerStatus> getDeleteVectorMethod() {
    io.grpc.MethodDescriptor<org.example.ArrowFabric.GetVector, org.example.ArrowFabric.ServerStatus> getDeleteVectorMethod;
    if ((getDeleteVectorMethod = VectorServiceGrpc.getDeleteVectorMethod) == null) {
      synchronized (VectorServiceGrpc.class) {
        if ((getDeleteVectorMethod = VectorServiceGrpc.getDeleteVectorMethod) == null) {
          VectorServiceGrpc.getDeleteVectorMethod = getDeleteVectorMethod =
              io.grpc.MethodDescriptor.<org.example.ArrowFabric.GetVector, org.example.ArrowFabric.ServerStatus>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "deleteVector"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  org.example.ArrowFabric.GetVector.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  org.example.ArrowFabric.ServerStatus.getDefaultInstance()))
              .setSchemaDescriptor(new VectorServiceMethodDescriptorSupplier("deleteVector"))
              .build();
        }
      }
    }
    return getDeleteVectorMethod;
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
    public void createVector(org.example.ArrowFabric.CreateVector request,
        io.grpc.stub.StreamObserver<org.example.ArrowFabric.ServerStatus> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getCreateVectorMethod(), responseObserver);
    }

    /**
     */
    public io.grpc.stub.StreamObserver<org.example.ArrowFabric.SetVector> setVector(
        io.grpc.stub.StreamObserver<org.example.ArrowFabric.ServerStatus> responseObserver) {
      return io.grpc.stub.ServerCalls.asyncUnimplementedStreamingCall(getSetVectorMethod(), responseObserver);
    }

    /**
     */
    public void getVector(org.example.ArrowFabric.GetVector request,
        io.grpc.stub.StreamObserver<org.example.ArrowFabric.SetVector> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetVectorMethod(), responseObserver);
    }

    /**
     */
    public void deleteVector(org.example.ArrowFabric.GetVector request,
        io.grpc.stub.StreamObserver<org.example.ArrowFabric.ServerStatus> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getDeleteVectorMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getCreateVectorMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                org.example.ArrowFabric.CreateVector,
                org.example.ArrowFabric.ServerStatus>(
                  this, METHODID_CREATE_VECTOR)))
          .addMethod(
            getSetVectorMethod(),
            io.grpc.stub.ServerCalls.asyncClientStreamingCall(
              new MethodHandlers<
                org.example.ArrowFabric.SetVector,
                org.example.ArrowFabric.ServerStatus>(
                  this, METHODID_SET_VECTOR)))
          .addMethod(
            getGetVectorMethod(),
            io.grpc.stub.ServerCalls.asyncServerStreamingCall(
              new MethodHandlers<
                org.example.ArrowFabric.GetVector,
                org.example.ArrowFabric.SetVector>(
                  this, METHODID_GET_VECTOR)))
          .addMethod(
            getDeleteVectorMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                org.example.ArrowFabric.GetVector,
                org.example.ArrowFabric.ServerStatus>(
                  this, METHODID_DELETE_VECTOR)))
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
    public void createVector(org.example.ArrowFabric.CreateVector request,
        io.grpc.stub.StreamObserver<org.example.ArrowFabric.ServerStatus> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getCreateVectorMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public io.grpc.stub.StreamObserver<org.example.ArrowFabric.SetVector> setVector(
        io.grpc.stub.StreamObserver<org.example.ArrowFabric.ServerStatus> responseObserver) {
      return io.grpc.stub.ClientCalls.asyncClientStreamingCall(
          getChannel().newCall(getSetVectorMethod(), getCallOptions()), responseObserver);
    }

    /**
     */
    public void getVector(org.example.ArrowFabric.GetVector request,
        io.grpc.stub.StreamObserver<org.example.ArrowFabric.SetVector> responseObserver) {
      io.grpc.stub.ClientCalls.asyncServerStreamingCall(
          getChannel().newCall(getGetVectorMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void deleteVector(org.example.ArrowFabric.GetVector request,
        io.grpc.stub.StreamObserver<org.example.ArrowFabric.ServerStatus> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getDeleteVectorMethod(), getCallOptions()), request, responseObserver);
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
    public org.example.ArrowFabric.ServerStatus createVector(org.example.ArrowFabric.CreateVector request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getCreateVectorMethod(), getCallOptions(), request);
    }

    /**
     */
    public java.util.Iterator<org.example.ArrowFabric.SetVector> getVector(
        org.example.ArrowFabric.GetVector request) {
      return io.grpc.stub.ClientCalls.blockingServerStreamingCall(
          getChannel(), getGetVectorMethod(), getCallOptions(), request);
    }

    /**
     */
    public org.example.ArrowFabric.ServerStatus deleteVector(org.example.ArrowFabric.GetVector request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getDeleteVectorMethod(), getCallOptions(), request);
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
    public com.google.common.util.concurrent.ListenableFuture<org.example.ArrowFabric.ServerStatus> createVector(
        org.example.ArrowFabric.CreateVector request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getCreateVectorMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<org.example.ArrowFabric.ServerStatus> deleteVector(
        org.example.ArrowFabric.GetVector request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getDeleteVectorMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_CREATE_VECTOR = 0;
  private static final int METHODID_GET_VECTOR = 1;
  private static final int METHODID_DELETE_VECTOR = 2;
  private static final int METHODID_SET_VECTOR = 3;

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
        case METHODID_CREATE_VECTOR:
          serviceImpl.createVector((org.example.ArrowFabric.CreateVector) request,
              (io.grpc.stub.StreamObserver<org.example.ArrowFabric.ServerStatus>) responseObserver);
          break;
        case METHODID_GET_VECTOR:
          serviceImpl.getVector((org.example.ArrowFabric.GetVector) request,
              (io.grpc.stub.StreamObserver<org.example.ArrowFabric.SetVector>) responseObserver);
          break;
        case METHODID_DELETE_VECTOR:
          serviceImpl.deleteVector((org.example.ArrowFabric.GetVector) request,
              (io.grpc.stub.StreamObserver<org.example.ArrowFabric.ServerStatus>) responseObserver);
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
        case METHODID_SET_VECTOR:
          return (io.grpc.stub.StreamObserver<Req>) serviceImpl.setVector(
              (io.grpc.stub.StreamObserver<org.example.ArrowFabric.ServerStatus>) responseObserver);
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
              .addMethod(getCreateVectorMethod())
              .addMethod(getSetVectorMethod())
              .addMethod(getGetVectorMethod())
              .addMethod(getDeleteVectorMethod())
              .build();
        }
      }
    }
    return result;
  }
}
