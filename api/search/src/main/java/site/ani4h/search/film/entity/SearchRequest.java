package site.ani4h.search.film.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class SearchRequest {
    private String title;
    private String genre;
    private String uid;
    private Float score;
}
