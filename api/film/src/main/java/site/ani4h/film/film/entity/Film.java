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
public class Film {
    private Uid id;
    private String title;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String synopsis;
    private Images images;
    private Float avgStar;
    private Integer totalStar;
    private int maxEpisodes;
    private int numEpisodes;
    private int year;
    private String season;
    private Float averageEpisodeDuration;
    private Uid ageRatingId;
    private int status;
    private FilmState state;
    private AgeRating ageRating;
    private List<Genre> genres;
    private List<FilmCharacter> characters;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Uid seriesId;
    private Integer view;
    public static final int type = 3;
    public void setState(String state) {
        this.state = FilmState.fromString(state);
    }
    public void setAgeRatingId(int id) {
        this.ageRatingId = new Uid(id,0,AgeRating.type);
    }
    public void setId(int id) {
        this.id = new Uid(id,0,type);
    }
    public void setSeriesId(Integer seriesId) {
        if (seriesId != null) {
            this.seriesId = new Uid(seriesId,0,type);
        }
    }
}
