package site.ani4h.film.ageRating;

import lombok.Getter;
import lombok.Setter;
import site.ani4h.shared.common.Image;
import site.ani4h.shared.common.Uid;

@Getter
@Setter
public class AgeRatingUpdate {
    private String shortName;
    private String longName;
    private String description;
    private int minAgeToWatch;
    private Image image;
}
