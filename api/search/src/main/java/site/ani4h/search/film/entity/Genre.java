package site.ani4h.search.film.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Genre {
    private int id;
    private String name;

    public Genre() {}

    public Genre(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public GenreResponse mapToGenreResponse() {
        GenreResponse genreResponse = new GenreResponse();
        genreResponse.setId(id);
        genreResponse.setName(name);
        return genreResponse;
    }
}
