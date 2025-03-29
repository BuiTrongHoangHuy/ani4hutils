package site.ani4h.auth.user;

import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import site.ani4h.shared.UserGrpc;
import site.ani4h.shared.UserOuterClass;

@GrpcService
public class GrpcUserController extends UserGrpc.UserImplBase {
    @Override
    public void getUserById(UserOuterClass.GetUsersByIdsReq request,
                            StreamObserver<UserOuterClass.PublicUserInfo> responseObserver){
        var res = UserOuterClass.PublicUserInfo.newBuilder().setUserName("Cao").build();
        responseObserver.onNext(res);
        responseObserver.onCompleted();
    }
}
