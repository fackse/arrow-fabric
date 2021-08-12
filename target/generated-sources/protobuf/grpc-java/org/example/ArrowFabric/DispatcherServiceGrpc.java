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
  private static volatile io.grpc.MethodDescriptor<org.example.ArrowFabric.GetTicket,
      org.example.ArrowFabric.Ticket> getGetTicketMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "getTicket",
      requestType = org.example.ArrowFabric.GetTicket.class,
      responseType = org.example.ArrowFabric.Ticket.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<org.example.ArrowFabric.GetTicket,
      org.example.ArrowFabric.Ticket> getGetTicketMethod() {
    io.grpc.MethodDescriptor<org.example.ArrowFabric.GetTicket, org.example.ArrowFabric.Ticket> getGetTicketMethod;
    if ((getGetTicketMethod = DispatcherServiceGrpc.getGetTicketMethod) == null) {
      synchronized (DispatcherServiceGrpc.class) {
        if ((getGetTicketMethod = DispatcherServiceGrpc.getGetTicketMethod) == null) {
          DispatcherServiceGrpc.getGetTicketMethod = getGetTicketMethod =
              io.grpc.MethodDescriptor.<org.example.ArrowFabric.GetTicket, org.example.ArrowFabric.Ticket>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "getTicket"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  org.example.ArrowFabric.GetTicket.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  org.example.ArrowFabric.Ticket.getDefaultInstance()))
              .setSchemaDescriptor(new DispatcherServiceMethodDescriptorSupplier("getTicket"))
              .build();
        }
      }
    }
    return getGetTicketMethod;
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
    public void getTicket(org.example.ArrowFabric.GetTicket request,
        io.grpc.stub.StreamObserver<org.example.ArrowFabric.Ticket> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetTicketMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getGetTicketMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                org.example.ArrowFabric.GetTicket,
                org.example.ArrowFabric.Ticket>(
                  this, METHODID_GET_TICKET)))
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
    public void getTicket(org.example.ArrowFabric.GetTicket request,
        io.grpc.stub.StreamObserver<org.example.ArrowFabric.Ticket> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetTicketMethod(), getCallOptions()), request, responseObserver);
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
    public org.example.ArrowFabric.Ticket getTicket(org.example.ArrowFabric.GetTicket request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetTicketMethod(), getCallOptions(), request);
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
    public com.google.common.util.concurrent.ListenableFuture<org.example.ArrowFabric.Ticket> getTicket(
        org.example.ArrowFabric.GetTicket request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetTicketMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_GET_TICKET = 0;

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
        case METHODID_GET_TICKET:
          serviceImpl.getTicket((org.example.ArrowFabric.GetTicket) request,
              (io.grpc.stub.StreamObserver<org.example.ArrowFabric.Ticket>) responseObserver);
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
              .addMethod(getGetTicketMethod())
              .build();
        }
      }
    }
    return result;
  }
}
