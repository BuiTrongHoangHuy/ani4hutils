package site.ani4h.film.film;
import org.springframework.stereotype.Component;
import site.ani4h.share.common.Paging;

import java.util.List;

@Component
public interface FilmRepository {
    Film findById(int id);
    List<Film> getFilms(Paging paging);
}
