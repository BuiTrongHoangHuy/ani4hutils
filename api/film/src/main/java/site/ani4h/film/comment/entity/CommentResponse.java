package site.ani4h.film.comment.entity;

import lombok.Getter;
import lombok.Setter;
import site.ani4h.film.episode.entity.Episode;
import site.ani4h.film.film.entity.Film;
import site.ani4h.shared.common.Uid;

import java.time.LocalDateTime;

@Getter
@Setter
public class CommentResponse {

    private Uid id;
    private Uid filmId;
    private Uid userId;
    private Uid episodeId;
    private String content;
    private String displayName;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static final int type = 14;

    public void setId(int id) {
        this.id = new Uid(id, 0, type);
    }

    public void setFilmId(int id) {
        this.filmId = new Uid(id, 0, Film.type);
    }
    public void setUserId(int id) {
        this.userId = new Uid(id, 0, 1);
    }
    public void setEpisodeId(int id){
        this.episodeId = new Uid(id,0, Episode.type);
    }
}
