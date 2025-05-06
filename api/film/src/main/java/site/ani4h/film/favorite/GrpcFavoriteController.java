package site.ani4h.film.favorite;

import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import site.ani4h.shared.gen.FavoriteGrpc;
import site.ani4h.shared.gen.FavoriteOuterClass;

@GrpcService
public class GrpcFavoriteController extends FavoriteGrpc.FavoriteImplBase {
    private final FavoriteService favoriteService;

    public GrpcFavoriteController(FavoriteService favoriteService) {
        this.favoriteService = favoriteService;
    }

    @Override
    public void getListFilmIdRecentFavorite(
            FavoriteOuterClass.GetListFilmIdRecentFavoriteReq request,
            StreamObserver<FavoriteOuterClass.ListFilmIdRecentFavoriteRes> responseObserver
    ){
        var listFilmId = favoriteService.getRecentByUserId(request.getUserId(), request.getLimit());
        var res = FavoriteOuterClass.ListFilmIdRecentFavoriteRes.newBuilder()
                .addAllFilmId(listFilmId)
                .build();
        responseObserver.onNext(res);
        responseObserver.onCompleted();
    }
}
