package site.ani4h.search.film.entity;

import lombok.Getter;
import lombok.Setter;
import site.ani4h.shared.common.Uid;

@Getter
@Setter
public class UserBasedRequest {
    private String userId;
    private int seed;
}
