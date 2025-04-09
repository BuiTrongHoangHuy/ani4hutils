package site.ani4h.search.film;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import site.ani4h.shared.common.Requester;

import java.util.List;

@RestController
@RequestMapping("/v1/search")
public class FilmController {
    private final FilmService filmService;
    public FilmController(FilmService filmService) {
        this.filmService = filmService;
    }

    @PostMapping("/sync")
    public ResponseEntity<?> syncFilmsToElastic() {
        filmService.syncFilmsToElastic();
        return ResponseEntity.ok().build();
    }

    @GetMapping()
    public ResponseEntity<?> getFilmsByTitle(@RequestParam String title, HttpServletRequest request) throws Exception {
        var requester = Requester.getRequester(request);
        var films = filmService.getFilmsByTitle(title);
        return ResponseEntity.ok(requester);
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllFilms() {
        List<FilmModel> films = filmService.getFilms();
        return ResponseEntity.ok(films);
    }

    @GetMapping("/suggest")
    public ResponseEntity<?> getFilmsByTitleSuggest(
        @RequestParam String keyword,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int pageSize
    ) {
        var films = filmService.getFilmsByTitleSuggest(keyword, page, pageSize);
        return ResponseEntity.ok(films);
    }

    @GetMapping("/filter")
    public ResponseEntity<?> getFilmsByFilter(
        @RequestParam String genre,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int pageSize
    ) {
        var films = filmService.getFilmsByFilter(genre, page, pageSize);
        return ResponseEntity.ok(films);
    }
}
