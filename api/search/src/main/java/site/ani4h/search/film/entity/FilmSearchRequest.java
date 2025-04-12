package site.ani4h.search.film.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class FilmSearchRequest {
    private String title;
    private String genre;
    private String year;
    private String season;
}
