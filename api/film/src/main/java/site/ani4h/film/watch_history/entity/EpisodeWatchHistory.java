package site.ani4h.film.watch_history.entity;

import lombok.Getter;
import lombok.Setter;
import site.ani4h.shared.common.Image;
import site.ani4h.shared.common.Uid;

@Getter
@Setter
public class EpisodeWatchHistory {
    private Uid id;
    private String title;
    private int episodeNumber;
    private String synopsis;
    private int duration;
    private Image thumbnail;
    private int viewCount;
    private int watchedDuration;
    private int filmId;

    private final static int type = 4;

    public void setId(int id) {
        this.id = new Uid(id,0,type);
    }
}
