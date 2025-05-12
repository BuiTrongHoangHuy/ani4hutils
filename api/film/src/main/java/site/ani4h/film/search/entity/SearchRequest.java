package site.ani4h.film.search.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SearchRequest {
    private String title;
    private String genreId;
    private String state;
    private int year = 0;
    private String season;
}
