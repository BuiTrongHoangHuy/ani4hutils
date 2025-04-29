package site.ani4h.film.favorite;

import org.springframework.stereotype.Service;
import site.ani4h.film.favorite.entity.FavoriteFilm;
import site.ani4h.film.favorite.entity.FavoriteRequest;
import site.ani4h.shared.common.Paging;
import site.ani4h.shared.common.Uid;

import java.util.List;

@Service
public class FavoriteServiceImpl implements FavoriteService {
    private final FavoriteRepository favoriteRepository;

    public FavoriteServiceImpl(FavoriteRepository favoriteRepository) {
        this.favoriteRepository = favoriteRepository;
    }

    @Override
    public void addFavorite(FavoriteRequest request) {
        favoriteRepository.addFavorite(request.getUserId().getLocalId(), request.getFilmId().getLocalId());
    }

    @Override
    public void removeFavorite(FavoriteRequest request) {
        favoriteRepository.removeFavorite(request.getUserId().getLocalId(), request.getFilmId().getLocalId());
    }

    @Override
    public boolean isFavorite(FavoriteRequest request) {
        return favoriteRepository.isFavorite(request.getUserId().getLocalId(), request.getFilmId().getLocalId());
    }

    @Override
    public List<FavoriteFilm> getFavoriteFilms(Uid userId, Paging paging) {
        return favoriteRepository.getFavoriteFilms(userId.getLocalId(), paging);
    }
}
