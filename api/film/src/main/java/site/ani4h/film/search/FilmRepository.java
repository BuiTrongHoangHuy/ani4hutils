package site.ani4h.film.search;

import org.springframework.stereotype.Component;
import site.ani4h.film.search.entity.Film;

import java.util.List;

@Component
public interface FilmRepository {
    List<Film> getFilms();
}
