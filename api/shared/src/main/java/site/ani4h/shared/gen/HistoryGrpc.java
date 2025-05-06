package site.ani4h.shared.gen;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.71.0)",
    comments = "Source: site/ani4h/shared/proto/history.proto")
@io.grpc.stub.annotations.GrpcGenerated
public final class HistoryGrpc {

  private HistoryGrpc() {}

  public static final java.lang.String SERVICE_NAME = "History";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<site.ani4h.shared.gen.HistoryOuterClass.GetListFilmIdRecentHistoryReq,
      site.ani4h.shared.gen.HistoryOuterClass.ListFilmIdRecentHistoryRes> getGetListFilmIdRecentHistoryMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetListFilmIdRecentHistory",
      requestType = site.ani4h.shared.gen.HistoryOuterClass.GetListFilmIdRecentHistoryReq.class,
      responseType = site.ani4h.shared.gen.HistoryOuterClass.ListFilmIdRecentHistoryRes.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<site.ani4h.shared.gen.HistoryOuterClass.GetListFilmIdRecentHistoryReq,
      site.ani4h.shared.gen.HistoryOuterClass.ListFilmIdRecentHistoryRes> getGetListFilmIdRecentHistoryMethod() {
    io.grpc.MethodDescriptor<site.ani4h.shared.gen.HistoryOuterClass.GetListFilmIdRecentHistoryReq, site.ani4h.shared.gen.HistoryOuterClass.ListFilmIdRecentHistoryRes> getGetListFilmIdRecentHistoryMethod;
    if ((getGetListFilmIdRecentHistoryMethod = HistoryGrpc.getGetListFilmIdRecentHistoryMethod) == null) {
      synchronized (HistoryGrpc.class) {
        if ((getGetListFilmIdRecentHistoryMethod = HistoryGrpc.getGetListFilmIdRecentHistoryMethod) == null) {
          HistoryGrpc.getGetListFilmIdRecentHistoryMethod = getGetListFilmIdRecentHistoryMethod =
              io.grpc.MethodDescriptor.<site.ani4h.shared.gen.HistoryOuterClass.GetListFilmIdRecentHistoryReq, site.ani4h.shared.gen.HistoryOuterClass.ListFilmIdRecentHistoryRes>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GetListFilmIdRecentHistory"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  site.ani4h.shared.gen.HistoryOuterClass.GetListFilmIdRecentHistoryReq.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  site.ani4h.shared.gen.HistoryOuterClass.ListFilmIdRecentHistoryRes.getDefaultInstance()))
              .setSchemaDescriptor(new HistoryMethodDescriptorSupplier("GetListFilmIdRecentHistory"))
              .build();
        }
      }
    }
    return getGetListFilmIdRecentHistoryMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static HistoryStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<HistoryStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<HistoryStub>() {
        @java.lang.Override
        public HistoryStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new HistoryStub(channel, callOptions);
        }
      };
    return HistoryStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports all types of calls on the service
   */
  public static HistoryBlockingV2Stub newBlockingV2Stub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<HistoryBlockingV2Stub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<HistoryBlockingV2Stub>() {
        @java.lang.Override
        public HistoryBlockingV2Stub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new HistoryBlockingV2Stub(channel, callOptions);
        }
      };
    return HistoryBlockingV2Stub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static HistoryBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<HistoryBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<HistoryBlockingStub>() {
        @java.lang.Override
        public HistoryBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new HistoryBlockingStub(channel, callOptions);
        }
      };
    return HistoryBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static HistoryFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<HistoryFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<HistoryFutureStub>() {
        @java.lang.Override
        public HistoryFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new HistoryFutureStub(channel, callOptions);
        }
      };
    return HistoryFutureStub.newStub(factory, channel);
  }

  /**
   */
  public interface AsyncService {

    /**
     */
    default void getListFilmIdRecentHistory(site.ani4h.shared.gen.HistoryOuterClass.GetListFilmIdRecentHistoryReq request,
        io.grpc.stub.StreamObserver<site.ani4h.shared.gen.HistoryOuterClass.ListFilmIdRecentHistoryRes> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetListFilmIdRecentHistoryMethod(), responseObserver);
    }
  }

  /**
   * Base class for the server implementation of the service History.
   */
  public static abstract class HistoryImplBase
      implements io.grpc.BindableService, AsyncService {

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return HistoryGrpc.bindService(this);
    }
  }

  /**
   * A stub to allow clients to do asynchronous rpc calls to service History.
   */
  public static final class HistoryStub
      extends io.grpc.stub.AbstractAsyncStub<HistoryStub> {
    private HistoryStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected HistoryStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new HistoryStub(channel, callOptions);
    }

    /**
     */
    public void getListFilmIdRecentHistory(site.ani4h.shared.gen.HistoryOuterClass.GetListFilmIdRecentHistoryReq request,
        io.grpc.stub.StreamObserver<site.ani4h.shared.gen.HistoryOuterClass.ListFilmIdRecentHistoryRes> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetListFilmIdRecentHistoryMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   * A stub to allow clients to do synchronous rpc calls to service History.
   */
  public static final class HistoryBlockingV2Stub
      extends io.grpc.stub.AbstractBlockingStub<HistoryBlockingV2Stub> {
    private HistoryBlockingV2Stub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected HistoryBlockingV2Stub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new HistoryBlockingV2Stub(channel, callOptions);
    }

    /**
     */
    public site.ani4h.shared.gen.HistoryOuterClass.ListFilmIdRecentHistoryRes getListFilmIdRecentHistory(site.ani4h.shared.gen.HistoryOuterClass.GetListFilmIdRecentHistoryReq request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetListFilmIdRecentHistoryMethod(), getCallOptions(), request);
    }
  }

  /**
   * A stub to allow clients to do limited synchronous rpc calls to service History.
   */
  public static final class HistoryBlockingStub
      extends io.grpc.stub.AbstractBlockingStub<HistoryBlockingStub> {
    private HistoryBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected HistoryBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new HistoryBlockingStub(channel, callOptions);
    }

    /**
     */
    public site.ani4h.shared.gen.HistoryOuterClass.ListFilmIdRecentHistoryRes getListFilmIdRecentHistory(site.ani4h.shared.gen.HistoryOuterClass.GetListFilmIdRecentHistoryReq request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetListFilmIdRecentHistoryMethod(), getCallOptions(), request);
    }
  }

  /**
   * A stub to allow clients to do ListenableFuture-style rpc calls to service History.
   */
  public static final class HistoryFutureStub
      extends io.grpc.stub.AbstractFutureStub<HistoryFutureStub> {
    private HistoryFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected HistoryFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new HistoryFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<site.ani4h.shared.gen.HistoryOuterClass.ListFilmIdRecentHistoryRes> getListFilmIdRecentHistory(
        site.ani4h.shared.gen.HistoryOuterClass.GetListFilmIdRecentHistoryReq request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetListFilmIdRecentHistoryMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_GET_LIST_FILM_ID_RECENT_HISTORY = 0;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final AsyncService serviceImpl;
    private final int methodId;

    MethodHandlers(AsyncService serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_GET_LIST_FILM_ID_RECENT_HISTORY:
          serviceImpl.getListFilmIdRecentHistory((site.ani4h.shared.gen.HistoryOuterClass.GetListFilmIdRecentHistoryReq) request,
              (io.grpc.stub.StreamObserver<site.ani4h.shared.gen.HistoryOuterClass.ListFilmIdRecentHistoryRes>) responseObserver);
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

  public static final io.grpc.ServerServiceDefinition bindService(AsyncService service) {
    return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
        .addMethod(
          getGetListFilmIdRecentHistoryMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              site.ani4h.shared.gen.HistoryOuterClass.GetListFilmIdRecentHistoryReq,
              site.ani4h.shared.gen.HistoryOuterClass.ListFilmIdRecentHistoryRes>(
                service, METHODID_GET_LIST_FILM_ID_RECENT_HISTORY)))
        .build();
  }

  private static abstract class HistoryBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    HistoryBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return site.ani4h.shared.gen.HistoryOuterClass.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("History");
    }
  }

  private static final class HistoryFileDescriptorSupplier
      extends HistoryBaseDescriptorSupplier {
    HistoryFileDescriptorSupplier() {}
  }

  private static final class HistoryMethodDescriptorSupplier
      extends HistoryBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final java.lang.String methodName;

    HistoryMethodDescriptorSupplier(java.lang.String methodName) {
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
      synchronized (HistoryGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new HistoryFileDescriptorSupplier())
              .addMethod(getGetListFilmIdRecentHistoryMethod())
              .build();
        }
      }
    }
    return result;
  }
}
