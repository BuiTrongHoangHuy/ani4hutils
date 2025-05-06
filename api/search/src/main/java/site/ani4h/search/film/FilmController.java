package site.ani4h.search.film;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import site.ani4h.search.film.entity.ContentBasedRequest;
import site.ani4h.search.film.entity.Film;
import site.ani4h.search.film.entity.SearchRequest;
import site.ani4h.search.film.entity.UserBasedRequest;
import site.ani4h.shared.common.PagingSearch;
import site.ani4h.shared.common.Uid;

import java.util.List;

@RestController
@RequestMapping("/v1/search")
public class FilmController {
    private final FilmServiceImpl filmService;
    public FilmController(FilmServiceImpl filmService) {
        this.filmService = filmService;
    }

    @PostMapping("/sync")
    public ResponseEntity<?> syncFilmsToElastic() {
        filmService.syncFilmsToElastic();
        return ResponseEntity.ok().build();
    }

    @GetMapping("/content-based")
    public ResponseEntity<?> contentBasedRecommendMLT(@ModelAttribute ContentBasedRequest request, @ModelAttribute PagingSearch paging) {
        var films = filmService.contentBasedRecommendMLT(request, paging);
        return ResponseEntity.ok(films);
    }

    @GetMapping("/user-favorite")
    public ResponseEntity<?> userFavoriteRecommendMLT(@RequestParam UserBasedRequest request, @ModelAttribute PagingSearch paging) {
        var films = filmService.userFavoriteRecommendMLT(request, paging);
        return ResponseEntity.ok(films);
    }

    @GetMapping("/user-history")
    public ResponseEntity<?> userHistoryRecommendMLT(@RequestParam UserBasedRequest request, @ModelAttribute PagingSearch paging) {
        var films = filmService.userHistoryRecommendMLT(request, paging);
        return ResponseEntity.ok(films);
    }

    @GetMapping()
    public ResponseEntity<?> getFilmsByTitle(@ModelAttribute SearchRequest request, @ModelAttribute PagingSearch paging) {
        var films = filmService.searchFilms(request, paging);
        return ResponseEntity.ok(films);
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllFilms() {
        List<Film> films = filmService.getFilms();
        return ResponseEntity.ok(films);
    }

    @GetMapping("/random")
    public ResponseEntity<?> getRandomFilmIds(@RequestParam int size) {
        List<Integer> filmIds = filmService.randomFilmIds(size);
        return ResponseEntity.ok(filmIds);
    }
}
