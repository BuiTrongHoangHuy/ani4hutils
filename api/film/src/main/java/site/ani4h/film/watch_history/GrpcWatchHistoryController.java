package site.ani4h.film.watch_history;

import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import site.ani4h.shared.gen.HistoryGrpc;
import site.ani4h.shared.gen.HistoryOuterClass;

@GrpcService
public class GrpcWatchHistoryController extends HistoryGrpc.HistoryImplBase {
    private final WatchHistoryService watchHistoryService;

    public GrpcWatchHistoryController(WatchHistoryService watchHistoryService) {
        this.watchHistoryService = watchHistoryService;
    }

    @Override
    public void getListFilmIdRecentHistory(HistoryOuterClass.GetListFilmIdRecentHistoryReq request,
                                           StreamObserver<HistoryOuterClass.ListFilmIdRecentHistoryRes> responseObserver) {
        var listFilmId = watchHistoryService.getRecentByUserId(request.getUserId(), request.getLimit());
        var res = HistoryOuterClass.ListFilmIdRecentHistoryRes.newBuilder()
                .addAllFilmId(listFilmId)
                .build();
        responseObserver.onNext(res);
        responseObserver.onCompleted();
    }
}
