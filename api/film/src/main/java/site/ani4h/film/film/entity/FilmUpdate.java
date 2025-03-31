package site.ani4h.film.film.entity;

import lombok.Getter;
import lombok.Setter;
import site.ani4h.shared.common.Image;
import site.ani4h.shared.common.Uid;
import java.time.LocalDateTime;

@Getter
@Setter
public class FilmUpdate {
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
    private FilmSeason season;
    private Float averageEpisodeDuration;
    private Uid ageRatingId;
    private int status;
    private FilmState state;
    private int seriesId;
}
