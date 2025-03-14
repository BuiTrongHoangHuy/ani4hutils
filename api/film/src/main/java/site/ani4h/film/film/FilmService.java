package site.ani4h.film.film;

import org.springframework.stereotype.Service;

@Service
public class FilmService {
    final FilmRepository filmRepository;

    public FilmService(FilmRepository filmRepository) {
        this.filmRepository = filmRepository;
    }
}
