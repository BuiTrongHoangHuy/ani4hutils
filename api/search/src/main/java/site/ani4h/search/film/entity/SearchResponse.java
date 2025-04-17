package site.ani4h.search.film.entity;

import lombok.Getter;
import lombok.Setter;
import site.ani4h.shared.common.PagingSearch;

import java.util.List;

@Getter
@Setter
public class SearchResponse {
    List<Film> data;
    PagingSearch paging;

    public SearchResponse() {
    }

    public SearchResponse(List<Film> data, PagingSearch paging) {
        this.data = data;
        this.paging = paging;
    }
}
