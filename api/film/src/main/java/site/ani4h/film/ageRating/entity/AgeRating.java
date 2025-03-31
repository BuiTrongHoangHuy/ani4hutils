package site.ani4h.film.ageRating.entity;

import lombok.Getter;
import lombok.Setter;
import site.ani4h.shared.common.Image;
import site.ani4h.shared.common.Uid;

import java.time.LocalDateTime;

@Getter
@Setter
public class AgeRating {
    private Uid id;
    private String shortName;
    private String longName;
    private String description;
    private Image image;
    private int minAgeToWatch;
    private int status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    public static final int type = 4;
    public void setId(int id ) {
        this.id = new Uid(id,0,type);
    }
}
