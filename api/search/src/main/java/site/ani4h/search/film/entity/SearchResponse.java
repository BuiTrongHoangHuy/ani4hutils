package site.ani4h.search.film.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class SearchResponse {
    List<FilmResponse> data;
    PagingSearch paging;
}
