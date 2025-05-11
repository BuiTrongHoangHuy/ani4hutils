package site.ani4h.film.comment;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import site.ani4h.film.comment.entity.CommentRequest;
import site.ani4h.film.comment.entity.CommentResponse;
import site.ani4h.shared.common.Paging;
import java.util.List;

@Repository
public class JdbcCommentRepository implements CommentRepository {
    private final JdbcTemplate jdbcTemplate;

    public JdbcCommentRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void addComment(CommentRequest request) {
        String sql = "INSERT INTO comments (film_id, user_id, episode_id, content) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(
                sql,
                request.getFilmId().getLocalId(),
                request.getUserId().getLocalId(),
                request.getEpisodeId() != null ? request.getEpisodeId().getLocalId() : null,
                request.getContent()
        );
    }

    @Override
    public void updateComment(CommentRequest request, int commentId) {
        String sql = "UPDATE comments SET content = ? WHERE id = ? AND user_id = ?";
        jdbcTemplate.update(
                sql,
                request.getContent(),
                commentId,
                request.getUserId().getLocalId()
        );
    }

    @Override
    public void deleteComment(int commentId) {
        String sql = "UPDATE comments SET status = 0 WHERE id = ?";
        jdbcTemplate.update(sql, commentId);
    }


    @Override
    public List<CommentResponse> getCommentsByFilmId(int filmId, Paging paging) {
        String sql = "SELECT c.*, u.display_name FROM comments c " +
                "JOIN users u ON c.user_id = u.id " +
                "WHERE c.film_id = ? AND c.status = 1 " +
                "ORDER BY c.created_at DESC " +
                "LIMIT ? OFFSET ?";
        return jdbcTemplate.query(
                sql, 
                new BeanPropertyRowMapper<>(CommentResponse.class), 
                filmId, 
                paging.getPageSize(), 
                paging.getOffset()
        );
    }

    @Override
    public List<CommentResponse> getCommentsByEpisodeId(int episodeId, Paging paging) {
        String sql = "SELECT c.*, u.display_name FROM comments c " +
                "JOIN users u ON c.user_id = u.id " +
                "WHERE c.episode_id = ? AND c.status = 1 " +
                "ORDER BY c.created_at DESC " +
                "LIMIT ? OFFSET ?";
        return jdbcTemplate.query(
                sql, 
                new BeanPropertyRowMapper<>(CommentResponse.class), 
                episodeId, 
                paging.getPageSize(), 
                paging.getOffset()
        );
    }

}
