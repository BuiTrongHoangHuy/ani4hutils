package site.ani4h.film.rating;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import site.ani4h.film.rating.entity.RatingRequest;
import site.ani4h.film.rating.entity.RatingResponse;

import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class JdbcRatingRepository implements RatingRepository {
    private final JdbcTemplate jdbcTemplate;

    public JdbcRatingRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void saveRating(RatingRequest request) {
        String sql = "INSERT INTO user_ratings (film_id, user_id, rating) VALUES (?, ?, ?)";
        jdbcTemplate.update(
                sql,
                request.getFilmId().getLocalId(),
                request.getUserId().getLocalId(),
                request.getRating()
        );
        updateAverageRating(request.getFilmId().getLocalId());
    }

    @Override
    public void updateRating(RatingRequest request) {
        String sql = "UPDATE user_ratings SET rating = ? WHERE film_id = ? AND user_id = ?";
        jdbcTemplate.update(
                sql,
                request.getRating(),
                request.getFilmId().getLocalId(),
                request.getUserId().getLocalId()
        );
        updateAverageRating(request.getFilmId().getLocalId());
    }

    @Override
    public boolean isExistRating(RatingRequest request) {
        String sql = "SELECT COUNT(*) FROM user_ratings WHERE film_id = ? AND user_id = ?";
        Integer count = jdbcTemplate.queryForObject(
                sql,
                new Object[]{request.getFilmId().getLocalId(), request.getUserId().getLocalId()},
                Integer.class
        );
        return count != null && count > 0;
    }

    @Override
    public void updateAverageRating(int filmId) {
        String sql = "UPDATE films SET avg_star = (SELECT AVG(rating) FROM user_ratings WHERE film_id = ?) WHERE id = ?";
        jdbcTemplate.update(sql, filmId, filmId);
    }

    @Override
    public RatingResponse getRatingByUserIdAndFilmId(int userId, int filmId) {
        String sql = "SELECT * FROM user_ratings WHERE user_id = ? AND film_id = ?";
        try {
            return jdbcTemplate.queryForObject(sql, new Object[]{userId, filmId}, new RatingRowMapper());
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    private static class RatingRowMapper implements RowMapper<RatingResponse> {
        @Override
        public RatingResponse mapRow(ResultSet rs, int rowNum) throws SQLException {
            RatingResponse rating = new RatingResponse();
            rating.setId(rs.getInt("id"));
            rating.setUserId(rs.getInt("user_id"));
            rating.setFilmId(rs.getInt("film_id"));
            rating.setRating(rs.getInt("rating"));
            rating.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
            rating.setUpdatedAt(rs.getTimestamp("updated_at").toLocalDateTime());
            return rating;
        }
    }
}
