package site.ani4h.film.ageRating.entity;

import lombok.Getter;
import lombok.Setter;
import site.ani4h.shared.common.Image;

@Getter
@Setter
public class AgeRatingUpdate {
    private String shortName;
    private String longName;
    private String description;
    private int minAgeToWatch;
    private Image image;
}
