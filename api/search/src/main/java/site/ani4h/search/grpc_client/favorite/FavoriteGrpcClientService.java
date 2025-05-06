package site.ani4h.search.grpc_client.favorite;

import java.util.List;

public interface FavoriteGrpcClientService {
    List<Integer> getFilmIdRecentFavorites(int userId, int limit);
}
