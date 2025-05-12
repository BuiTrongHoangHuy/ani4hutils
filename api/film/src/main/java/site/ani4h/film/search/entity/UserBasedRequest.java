package site.ani4h.film.search.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserBasedRequest {
    private String userId;
    private int seed;
}
