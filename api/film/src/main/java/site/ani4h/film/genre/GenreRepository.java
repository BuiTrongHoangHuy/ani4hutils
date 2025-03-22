package site.ani4h.film.genre;

import org.springframework.stereotype.Component;
import site.ani4h.share.common.Paging;

import java.util.List;
@Component
public interface GenreRepository {
    void create(GenreCreate genreCreate);
    List<Genre> getGenre();
    void update(int id ,GenreUpdate genreUpdate);
    void delete(int id );
}
