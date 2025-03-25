package site.ani4h.film.film;

import org.springframework.stereotype.Repository;
import site.ani4h.film.film.entity.FilmFilter;
import site.ani4h.film.film.entity.FilmList;
import site.ani4h.shared.common.Paging;

import java.util.List;

@Repository
public interface FilmRepository {
    List<FilmList> getFilms(Paging paging, FilmFilter filter);
}
