package site.ani4h.film.episode.entity;

import lombok.Getter;
import lombok.Setter;
import site.ani4h.shared.common.Image;
import site.ani4h.shared.common.Uid;

import java.time.LocalDateTime;

@Getter
@Setter
public class EpisodeUpdate {
    private Uid id;
    private String title;
    private int episodeNumber;
    private String synopsis;
    private int duration;
    private Image thumbnail;
    private String videoUrl;
    private LocalDateTime airDate;
    private String state;
}