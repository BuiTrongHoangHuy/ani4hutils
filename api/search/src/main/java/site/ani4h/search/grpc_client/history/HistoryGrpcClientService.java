package site.ani4h.search.grpc_client.history;

import java.util.List;

public interface HistoryGrpcClientService {
    List<Integer> getListFilmIdRecentHistory(int userId, int limit);
}
