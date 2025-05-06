package site.ani4h.film.favorite.entity;

import lombok.Getter;
import lombok.Setter;
import site.ani4h.shared.common.Uid;

@Getter
@Setter
public class FavoriteRequest {
    private Uid userId;
    private Uid filmId;
}
