package site.ani4h.shared.gen;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.71.0)",
    comments = "Source: site/ani4h/shared/proto/favorite.proto")
@io.grpc.stub.annotations.GrpcGenerated
public final class FavoriteGrpc {

  private FavoriteGrpc() {}

  public static final java.lang.String SERVICE_NAME = "Favorite";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<site.ani4h.shared.gen.FavoriteOuterClass.GetListFilmIdRecentFavoriteReq,
      site.ani4h.shared.gen.FavoriteOuterClass.ListFilmIdRecentFavoriteRes> getGetListFilmIdRecentFavoriteMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetListFilmIdRecentFavorite",
      requestType = site.ani4h.shared.gen.FavoriteOuterClass.GetListFilmIdRecentFavoriteReq.class,
      responseType = site.ani4h.shared.gen.FavoriteOuterClass.ListFilmIdRecentFavoriteRes.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<site.ani4h.shared.gen.FavoriteOuterClass.GetListFilmIdRecentFavoriteReq,
      site.ani4h.shared.gen.FavoriteOuterClass.ListFilmIdRecentFavoriteRes> getGetListFilmIdRecentFavoriteMethod() {
    io.grpc.MethodDescriptor<site.ani4h.shared.gen.FavoriteOuterClass.GetListFilmIdRecentFavoriteReq, site.ani4h.shared.gen.FavoriteOuterClass.ListFilmIdRecentFavoriteRes> getGetListFilmIdRecentFavoriteMethod;
    if ((getGetListFilmIdRecentFavoriteMethod = FavoriteGrpc.getGetListFilmIdRecentFavoriteMethod) == null) {
      synchronized (FavoriteGrpc.class) {
        if ((getGetListFilmIdRecentFavoriteMethod = FavoriteGrpc.getGetListFilmIdRecentFavoriteMethod) == null) {
          FavoriteGrpc.getGetListFilmIdRecentFavoriteMethod = getGetListFilmIdRecentFavoriteMethod =
              io.grpc.MethodDescriptor.<site.ani4h.shared.gen.FavoriteOuterClass.GetListFilmIdRecentFavoriteReq, site.ani4h.shared.gen.FavoriteOuterClass.ListFilmIdRecentFavoriteRes>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GetListFilmIdRecentFavorite"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  site.ani4h.shared.gen.FavoriteOuterClass.GetListFilmIdRecentFavoriteReq.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  site.ani4h.shared.gen.FavoriteOuterClass.ListFilmIdRecentFavoriteRes.getDefaultInstance()))
              .setSchemaDescriptor(new FavoriteMethodDescriptorSupplier("GetListFilmIdRecentFavorite"))
              .build();
        }
      }
    }
    return getGetListFilmIdRecentFavoriteMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static FavoriteStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<FavoriteStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<FavoriteStub>() {
        @java.lang.Override
        public FavoriteStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new FavoriteStub(channel, callOptions);
        }
      };
    return FavoriteStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports all types of calls on the service
   */
  public static FavoriteBlockingV2Stub newBlockingV2Stub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<FavoriteBlockingV2Stub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<FavoriteBlockingV2Stub>() {
        @java.lang.Override
        public FavoriteBlockingV2Stub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new FavoriteBlockingV2Stub(channel, callOptions);
        }
      };
    return FavoriteBlockingV2Stub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static FavoriteBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<FavoriteBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<FavoriteBlockingStub>() {
        @java.lang.Override
        public FavoriteBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new FavoriteBlockingStub(channel, callOptions);
        }
      };
    return FavoriteBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static FavoriteFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<FavoriteFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<FavoriteFutureStub>() {
        @java.lang.Override
        public FavoriteFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new FavoriteFutureStub(channel, callOptions);
        }
      };
    return FavoriteFutureStub.newStub(factory, channel);
  }

  /**
   */
  public interface AsyncService {

    /**
     */
    default void getListFilmIdRecentFavorite(site.ani4h.shared.gen.FavoriteOuterClass.GetListFilmIdRecentFavoriteReq request,
        io.grpc.stub.StreamObserver<site.ani4h.shared.gen.FavoriteOuterClass.ListFilmIdRecentFavoriteRes> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetListFilmIdRecentFavoriteMethod(), responseObserver);
    }
  }

  /**
   * Base class for the server implementation of the service Favorite.
   */
  public static abstract class FavoriteImplBase
      implements io.grpc.BindableService, AsyncService {

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return FavoriteGrpc.bindService(this);
    }
  }

  /**
   * A stub to allow clients to do asynchronous rpc calls to service Favorite.
   */
  public static final class FavoriteStub
      extends io.grpc.stub.AbstractAsyncStub<FavoriteStub> {
    private FavoriteStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected FavoriteStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new FavoriteStub(channel, callOptions);
    }

    /**
     */
    public void getListFilmIdRecentFavorite(site.ani4h.shared.gen.FavoriteOuterClass.GetListFilmIdRecentFavoriteReq request,
        io.grpc.stub.StreamObserver<site.ani4h.shared.gen.FavoriteOuterClass.ListFilmIdRecentFavoriteRes> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetListFilmIdRecentFavoriteMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   * A stub to allow clients to do synchronous rpc calls to service Favorite.
   */
  public static final class FavoriteBlockingV2Stub
      extends io.grpc.stub.AbstractBlockingStub<FavoriteBlockingV2Stub> {
    private FavoriteBlockingV2Stub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected FavoriteBlockingV2Stub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new FavoriteBlockingV2Stub(channel, callOptions);
    }

    /**
     */
    public site.ani4h.shared.gen.FavoriteOuterClass.ListFilmIdRecentFavoriteRes getListFilmIdRecentFavorite(site.ani4h.shared.gen.FavoriteOuterClass.GetListFilmIdRecentFavoriteReq request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetListFilmIdRecentFavoriteMethod(), getCallOptions(), request);
    }
  }

  /**
   * A stub to allow clients to do limited synchronous rpc calls to service Favorite.
   */
  public static final class FavoriteBlockingStub
      extends io.grpc.stub.AbstractBlockingStub<FavoriteBlockingStub> {
    private FavoriteBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected FavoriteBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new FavoriteBlockingStub(channel, callOptions);
    }

    /**
     */
    public site.ani4h.shared.gen.FavoriteOuterClass.ListFilmIdRecentFavoriteRes getListFilmIdRecentFavorite(site.ani4h.shared.gen.FavoriteOuterClass.GetListFilmIdRecentFavoriteReq request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetListFilmIdRecentFavoriteMethod(), getCallOptions(), request);
    }
  }

  /**
   * A stub to allow clients to do ListenableFuture-style rpc calls to service Favorite.
   */
  public static final class FavoriteFutureStub
      extends io.grpc.stub.AbstractFutureStub<FavoriteFutureStub> {
    private FavoriteFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected FavoriteFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new FavoriteFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<site.ani4h.shared.gen.FavoriteOuterClass.ListFilmIdRecentFavoriteRes> getListFilmIdRecentFavorite(
        site.ani4h.shared.gen.FavoriteOuterClass.GetListFilmIdRecentFavoriteReq request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetListFilmIdRecentFavoriteMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_GET_LIST_FILM_ID_RECENT_FAVORITE = 0;

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
        case METHODID_GET_LIST_FILM_ID_RECENT_FAVORITE:
          serviceImpl.getListFilmIdRecentFavorite((site.ani4h.shared.gen.FavoriteOuterClass.GetListFilmIdRecentFavoriteReq) request,
              (io.grpc.stub.StreamObserver<site.ani4h.shared.gen.FavoriteOuterClass.ListFilmIdRecentFavoriteRes>) responseObserver);
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
          getGetListFilmIdRecentFavoriteMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              site.ani4h.shared.gen.FavoriteOuterClass.GetListFilmIdRecentFavoriteReq,
              site.ani4h.shared.gen.FavoriteOuterClass.ListFilmIdRecentFavoriteRes>(
                service, METHODID_GET_LIST_FILM_ID_RECENT_FAVORITE)))
        .build();
  }

  private static abstract class FavoriteBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    FavoriteBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return site.ani4h.shared.gen.FavoriteOuterClass.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("Favorite");
    }
  }

  private static final class FavoriteFileDescriptorSupplier
      extends FavoriteBaseDescriptorSupplier {
    FavoriteFileDescriptorSupplier() {}
  }

  private static final class FavoriteMethodDescriptorSupplier
      extends FavoriteBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final java.lang.String methodName;

    FavoriteMethodDescriptorSupplier(java.lang.String methodName) {
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
      synchronized (FavoriteGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new FavoriteFileDescriptorSupplier())
              .addMethod(getGetListFilmIdRecentFavoriteMethod())
              .build();
        }
      }
    }
    return result;
  }
}
