package site.ani4h.film.episode.entity;

import lombok.Getter;
import lombok.Setter;
import site.ani4h.film.film.entity.Film;
import site.ani4h.shared.common.Image;
import site.ani4h.shared.common.Uid;

import java.time.LocalDateTime;

@Getter
@Setter
public class Episode {
    private Uid id;
    private String title;
    private int episodeNumber;
    private String synopsis;
    private int duration;
    private Image thumbnail;
    private String videoUrl;
    private int viewCount;
    private LocalDateTime airDate;
    private EpisodeState state;
    private int status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Uid filmId;
    
    public static final int type = 13;
    
    public void setState(String state) {
        this.state = EpisodeState.fromString(state);
    }
    
    public void setId(int id) {
        this.id = new Uid(id, 0, type);
    }
    
    public void setFilmId(int id) {
        this.filmId = new Uid(id, 0, Film.type);
    }
}