package site.ani4h.film.film.entity;

import lombok.Getter;
import lombok.Setter;
import site.ani4h.shared.common.Image;
import site.ani4h.shared.common.Uid;
@Getter
@Setter
public class AgeRating {
    private Uid id;
    private String shortName;
    private String longName;
    private String description;
    private Image image;
    private int minAgeToWatch;
    public static final int type = 4;
    public void setId(int id ) {
        this.id = new Uid(id,0,type);
    }
}