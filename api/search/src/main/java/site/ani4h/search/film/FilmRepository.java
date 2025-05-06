package site.ani4h.search.film;

import org.springframework.stereotype.Component;
import site.ani4h.search.film.entity.Film;

import java.util.List;

@Component
public interface FilmRepository {
    List<Film> getFilms();
}
