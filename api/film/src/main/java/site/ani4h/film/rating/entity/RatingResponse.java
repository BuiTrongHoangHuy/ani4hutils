package site.ani4h.film.rating.entity;

import lombok.Getter;
import lombok.Setter;
import site.ani4h.film.film.entity.Film;
import site.ani4h.shared.common.Uid;

import java.time.LocalDateTime;

@Getter
@Setter
public class RatingResponse {
    private Uid id;
    private Uid userId;
    private Uid filmId;
    private int rating;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    
    public RatingResponse() {
    }
    public static final int type = 20;
    public void setId(int id) {
        this.id = new Uid(id, 0, type);
    }
    public void setFilmId(int id) {
        this.filmId = new Uid(id, 0, Film.type);
    }
    public void setUserId(int id) {
        this.userId = new Uid(id, 0, 1);
    }

    public RatingResponse(Uid id, Uid userId, Uid filmId, int rating, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.userId = userId;
        this.filmId = filmId;
        this.rating = rating;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}