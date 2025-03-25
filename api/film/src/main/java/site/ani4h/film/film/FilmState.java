package site.ani4h.film.film;
// enum ('upcoming','on_air','now_streaming','finished')
public enum FilmState {
    UPCOMING(){},
    ON_AIR(){},
    NOW_STREAMING(){},
    FINISHED(){};
    public static FilmState fromString(String state) {
        return valueOf(state.toUpperCase());
    }
}
