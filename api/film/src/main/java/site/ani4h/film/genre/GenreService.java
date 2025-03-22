package site.ani4h.film.genre;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GenreService {
    private final GenreRepository genreRepository;
    public GenreService(GenreRepository genreRepository) {
        this.genreRepository = genreRepository;
    }


    public void create(GenreCreate create) {
        genreRepository.create(create);
    }

    public List<Genre> getAll() {
        return genreRepository.getGenre();
    }

    public void update(int id,GenreUpdate update) {
        genreRepository.update(id, update);
    }


}
