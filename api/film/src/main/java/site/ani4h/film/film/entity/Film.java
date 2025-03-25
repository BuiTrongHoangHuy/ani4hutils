package site.ani4h.film.film.entity;

import lombok.Getter;
import lombok.Setter;
import site.ani4h.shared.common.Image;
import site.ani4h.shared.common.Uid;

import java.time.LocalDateTime;

@Getter
@Setter
public class Film {
    private Uid id;
    private String title;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String synopsis;
    private Image images;
    private Float avgStar;
    private int totalStar;
    private int maxEpisodes;
    private int numEpisodes;
    private int year;
    private String season;
    private Float averageEpisodeDuration;
    private Uid ageRatingId;
    private int status;
    private FilmState state;
    private AgeRating ageRating;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private int series_id;
    public static final int type = 3;
    public void setId(int id) {
        this.id = new Uid(id,0,type);
    }
}
