package site.ani4h.film.comment;

import site.ani4h.film.comment.entity.CommentRequest;
import site.ani4h.film.comment.entity.CommentResponse;
import site.ani4h.shared.common.Paging;
import site.ani4h.shared.common.Uid;

import java.util.List;

public interface CommentService {
    void addComment(CommentRequest request);

    void updateComment(CommentRequest request, int commentId);

    void deleteComment(int commentId);

    List<CommentResponse> getCommentsByFilmId(int filmId, Paging paging);

    List<CommentResponse> getCommentsByEpisodeId(int episodeId, Paging paging);

}
