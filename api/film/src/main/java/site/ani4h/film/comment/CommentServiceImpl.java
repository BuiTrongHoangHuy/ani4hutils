package site.ani4h.film.comment;

import org.springframework.stereotype.Service;
import site.ani4h.film.comment.entity.CommentRequest;
import site.ani4h.film.comment.entity.CommentResponse;
import site.ani4h.shared.common.Paging;
import site.ani4h.shared.common.Uid;

import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;

    public CommentServiceImpl(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    @Override
    public void addComment(CommentRequest request) {
        commentRepository.addComment(request);
    }

    @Override
    public void updateComment(CommentRequest request, int commentId) {
        commentRepository.updateComment(request, commentId);
    }

    @Override
    public void deleteComment(int commentId) {
        commentRepository.deleteComment(commentId);
    }

    @Override
    public List<CommentResponse> getCommentsByFilmId(int filmId, Paging paging) {
        return commentRepository.getCommentsByFilmId(filmId, paging);
    }

    @Override
    public List<CommentResponse> getCommentsByEpisodeId(int episodeId, Paging paging) {
        return commentRepository.getCommentsByEpisodeId(episodeId, paging);
    }

}
