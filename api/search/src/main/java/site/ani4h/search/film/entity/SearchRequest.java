package site.ani4h.search.film.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import site.ani4h.shared.common.Uid;

@Getter
@Setter
public class SearchRequest {
    private String title;
    private String genreId;
    private String state;
    private int year = 0;
    private String season;
}
