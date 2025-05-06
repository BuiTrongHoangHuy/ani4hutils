package site.ani4h.film.favorite;

import org.springframework.stereotype.Service;
import site.ani4h.film.favorite.entity.FavoriteFilm;
import site.ani4h.film.favorite.entity.FavoriteRequest;
import site.ani4h.shared.common.Paging;
import site.ani4h.shared.common.Uid;

import java.util.List;

@Service
public interface FavoriteService {
    void addFavorite(FavoriteRequest request);

    void removeFavorite(FavoriteRequest request);

    boolean isFavorite(FavoriteRequest request);

    List<FavoriteFilm> getFavoriteFilms(Uid userId, Paging paging);

    List<Integer> getRecentByUserId(int userId, int limit);
}
