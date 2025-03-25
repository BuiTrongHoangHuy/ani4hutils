package site.ani4h.film.film.entity;

public enum FilmSeason {
    SPRING,
    SUMMER,
    FALL,
    WINTER;
    public static FilmSeason fromString(String season) {
        return valueOf(season.toUpperCase());
    }
}

