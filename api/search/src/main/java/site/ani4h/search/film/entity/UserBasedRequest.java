package site.ani4h.search.film.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserBasedRequest {
    private int userId;
    private int seed;
}
