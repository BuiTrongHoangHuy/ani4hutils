package site.ani4h.search.film;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import site.ani4h.search.film.entity.FilmModel;
import site.ani4h.search.film.entity.SearchRequest;
import site.ani4h.shared.common.PagingSearch;

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

    @GetMapping()
    public ResponseEntity<?> getFilmsByTitle(@ModelAttribute SearchRequest request, @ModelAttribute PagingSearch paging) {
        var films = filmService.searchFilms(request, paging);
        return ResponseEntity.ok(films);
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllFilms() {
        List<FilmModel> films = filmService.getFilms();
        return ResponseEntity.ok(films);
    }
}
