package site.ani4h.film.film;

import org.springframework.stereotype.Component;
import site.ani4h.film.film.entity.FilmFilter;
import site.ani4h.film.film.entity.FilmList;
import site.ani4h.shared.common.Paging;

import java.util.List;

@Component
public class FilmServiceImpl implements FilmService {
    private final FilmRepository filmRepository;

    public FilmServiceImpl(FilmRepository filmRepository) {
        this.filmRepository = filmRepository;
    }

    @Override
    public List<FilmList> getFilms(Paging paging, FilmFilter filter) {
        return filmRepository.getFilms(paging, filter);
    }

}
