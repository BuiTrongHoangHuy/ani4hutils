package site.ani4h.film.favorite;

import org.springframework.stereotype.Repository;
import site.ani4h.film.favorite.entity.FavoriteFilm;
import site.ani4h.shared.common.Paging;

import java.util.List;

@Repository
public interface FavoriteRepository {
    void addFavorite(int userId, int filmId);

    void removeFavorite(int userId, int filmId);

    boolean isFavorite(int userId, int filmId);

    List<FavoriteFilm> getFavoriteFilms(int userId, Paging paging);
}
