package site.ani4h.film.grpc_client.favorite;

import java.util.List;

public interface FavoriteGrpcClientService {
    List<Integer> getFilmIdRecentFavorites(int userId, int limit);
}
