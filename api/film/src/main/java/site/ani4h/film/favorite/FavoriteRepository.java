package site.ani4h.film.favorite;

import org.springframework.stereotype.Repository;

@Repository
public interface FavoriteRepository {
    void addFavorite(int userId, int filmId);

    void removeFavorite(int userId, int filmId);

    boolean isFavorite(int userId, int filmId);
}
