package site.ani4h.search.film;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface FilmRepository {
    List<FilmModel> getFilms();
}
