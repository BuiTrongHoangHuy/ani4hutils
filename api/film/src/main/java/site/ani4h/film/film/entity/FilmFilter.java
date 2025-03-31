package site.ani4h.film.film.entity;

import lombok.Getter;
import lombok.Setter;
import site.ani4h.shared.common.Uid;

@Getter
@Setter
public class FilmFilter {
    private Uid seriesId;
    private Uid ageRatingId;
    private int year;
    private FilmSeason  season;
}
