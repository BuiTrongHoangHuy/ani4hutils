package site.ani4h.search.film.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PagingSearch {
    int total;
    float score;
    String uid;

    public PagingSearch() {}

    public PagingSearch(int total, float score, String uid) {
        this.total = total;
        this.score = score;
        this.uid = uid;
    }
}
