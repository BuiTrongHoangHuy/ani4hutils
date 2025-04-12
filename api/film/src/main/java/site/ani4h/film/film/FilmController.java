package site.ani4h.film.film;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import site.ani4h.film.film.entity.Film;
import site.ani4h.film.film.entity.FilmFilter;
import site.ani4h.shared.common.Paging;
import site.ani4h.shared.common.Uid;

import java.util.List;

@RestController
@RequestMapping("v1/film")
public class FilmController {
    private final FilmService filmService;

    public FilmController(FilmService filmService) {
        this.filmService = filmService;
    }
    @GetMapping()
    public ResponseEntity<?> getFilms(@ModelAttribute FilmFilter filmFilter,@ModelAttribute Paging paging) {
        return ResponseEntity.ok(this.filmService.getFilms(paging, filmFilter));
    }
    @GetMapping("{id}")
    public ResponseEntity<?> getFilmById(@PathVariable("id") Uid id) {
        return ResponseEntity.ok(this.filmService.getFilmById(id.getLocalId()));
    }
}
