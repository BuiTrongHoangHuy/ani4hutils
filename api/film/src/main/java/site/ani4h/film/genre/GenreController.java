package site.ani4h.film.genre;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/genre")
public class GenreController {
    private final GenreService genreService;
    public GenreController(GenreService genreService) {
        this.genreService = genreService;
    }
    @PostMapping()
    public ResponseEntity<?>  createGenre(@RequestBody GenreCreate genre) {
        genreService.create(genre);
        return ResponseEntity.ok(genre.getId());
    }
    @GetMapping()
    public ResponseEntity<?>  getGenres() {
        var genres = genreService.getAll();
        return ResponseEntity.ok(genres);
    }
}
