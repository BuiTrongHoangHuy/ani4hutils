package site.ani4h.film.ageRating.entity;

import lombok.Getter;
import lombok.Setter;
import site.ani4h.shared.common.Image;
import site.ani4h.shared.common.Uid;

@Getter
@Setter
public class AgeRatingCreate {
    private Uid id;
    private String shortName;
    private String longName;
    private String description;
    private int minAgeToWatch;
    private Image image;
    public void setId(int id ) {
        this.id = new Uid(id,0,AgeRating.type);
    }
}
