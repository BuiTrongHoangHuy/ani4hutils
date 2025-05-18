package site.ani4h.film.film.entity;

import lombok.Getter;
import lombok.Setter;
import site.ani4h.shared.common.Uid;

import java.time.LocalDateTime;

@Getter
@Setter
public class Rating {
    private Uid id;
    private Uid userId;
    private Uid filmId;
    private int rating;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    
    public final static int type = 20; // Assuming 20 is not used by other entities
    
    public void setId(int id) {
        this.id = new Uid(id, 0, type);
    }
    
    public void setUserId(int id) {
        this.userId = new Uid(id, 0, 1); // Assuming 1 is the type for users
    }
    
    public void setFilmId(int id) {
        this.filmId = new Uid(id, 0, Film.type);
    }
}