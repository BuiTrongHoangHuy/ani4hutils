package site.ani4h.film.episode.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EpisodeRequest {
    private String userId;
    private String filmId;
    private int numberEpisode;
}
