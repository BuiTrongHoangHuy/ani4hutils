package site.ani4h.film.episode.entity;


public enum EpisodeState {
    RELEASED(){},
    UPCOMING(){};
    public static EpisodeState fromString(String state) {
        return state == null ? null : valueOf(state.toUpperCase());
    }
}