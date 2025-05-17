package site.ani4h.film.comment.entity;

import lombok.Getter;
import lombok.Setter;
import site.ani4h.shared.common.Uid;

@Getter
@Setter
public class CommentRequest {
    private Uid filmId;
    private Uid userId;
    private Uid episodeId;
    private String content;
}