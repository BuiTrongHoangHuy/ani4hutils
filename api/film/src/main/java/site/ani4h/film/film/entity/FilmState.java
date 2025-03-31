package site.ani4h.film.film.entity;
// enum ('upcoming','on_air','now_streaming','finished')
public enum FilmState {
    UPCOMING(){},
    ON_AIR(){},
    NOW_STREAMING(){},
    FINISHED(){};
    public static FilmState fromString(String state) {
        return state == null ? null : valueOf(state.toUpperCase());
    }
}
