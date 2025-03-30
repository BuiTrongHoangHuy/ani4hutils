package site.ani4h.auth.user;

import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import site.ani4h.shared.gen.UserGrpc;
import site.ani4h.shared.gen.UserOuterClass;

@GrpcService
public class GrpcUserController extends UserGrpc.UserImplBase {
    private final UserService userService;
    public GrpcUserController(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void getUserById(UserOuterClass.GetUsersByIdReq request,
                            StreamObserver<UserOuterClass.PublicUserInfo> responseObserver){
        var user = userService.getUserById(request.getId());
        var res = UserOuterClass.PublicUserInfo.newBuilder().setUserName(user.getFirstName() + user.getLastName()).build();
        responseObserver.onNext(res);
        responseObserver.onCompleted();
    }
}
