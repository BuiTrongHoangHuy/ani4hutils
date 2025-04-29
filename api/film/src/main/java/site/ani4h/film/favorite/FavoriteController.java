package site.ani4h.film.favorite;

import org.springframework.web.bind.annotation.*;
import site.ani4h.film.favorite.entity.FavoriteFilm;
import site.ani4h.film.favorite.entity.FavoriteRequest;
import site.ani4h.shared.common.Paging;
import site.ani4h.shared.common.Uid;

import java.util.List;

@RestController
@RequestMapping("v1/favorite")
public class FavoriteController {
    private final FavoriteService favoriteService;

    public FavoriteController(FavoriteService favoriteService) {
        this.favoriteService = favoriteService;
    }

    @PostMapping("/add")
    public void addFavorite(@RequestBody FavoriteRequest request) {
        favoriteService.addFavorite(request);
    }

    @DeleteMapping("/remove")
    public void removeFavorite(@RequestBody FavoriteRequest request) {
        favoriteService.removeFavorite(request);
    }

    @GetMapping("/is-favorite")
    public boolean isFavorite(@ModelAttribute FavoriteRequest request) {
        return favoriteService.isFavorite(request);
    }

    @GetMapping("/list")
    public List<FavoriteFilm> getFavoriteFilms(@RequestParam Uid userId, @ModelAttribute Paging paging) {
        return favoriteService.getFavoriteFilms(userId, paging);
    }
}
