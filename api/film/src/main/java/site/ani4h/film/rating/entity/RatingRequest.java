package site.ani4h.film.rating.entity;

import lombok.Getter;
import lombok.Setter;
import site.ani4h.shared.common.Uid;

@Getter
@Setter
public class RatingRequest {
    private Uid filmId;
    private Uid userId;
    private int rating;
}
