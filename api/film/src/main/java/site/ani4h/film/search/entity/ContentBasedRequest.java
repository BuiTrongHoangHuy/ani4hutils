package site.ani4h.film.search.entity;

import lombok.Getter;
import lombok.Setter;
import site.ani4h.shared.common.Uid;

@Getter
@Setter
public class ContentBasedRequest {
    private Uid filmId;
    private int seed;
}
