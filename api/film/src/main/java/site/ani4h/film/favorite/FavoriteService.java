package site.ani4h.film.favorite;

import org.springframework.stereotype.Service;
import site.ani4h.film.favorite.entity.FavoriteRequest;

@Service
public interface FavoriteService {
    void addFavorite(FavoriteRequest request);

    void removeFavorite(FavoriteRequest request);

    boolean isFavorite(FavoriteRequest request);
}
