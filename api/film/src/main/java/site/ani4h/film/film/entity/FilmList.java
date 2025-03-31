package site.ani4h.film.film.entity;

import lombok.Getter;
import lombok.Setter;
import site.ani4h.shared.common.Image;
import site.ani4h.shared.common.Images;
import site.ani4h.shared.common.Uid;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class FilmList {
    private String title;
    private String synopsis;
    private Images images;
    private Float avgStar;
    private Integer totalStar;
    private Integer maxEpisodes;
    private Integer numEpisodes;
    private Uid ageRatingId;
    private int status;
    private FilmState state;
    private AgeRating ageRating;
    private Uid seriesId;
    public void setState(String state) {
        this.state = FilmState.fromString(state);
    }
    public void setAgeRatingId(int id) {
        this.ageRatingId = new Uid(id,0,AgeRating.type);
    }
}
