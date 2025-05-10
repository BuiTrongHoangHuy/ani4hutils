package site.ani4h.film.rating;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import site.ani4h.film.rating.entity.RatingRequest;

import java.util.List;

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
        // Update the average rating after inserting a new rating
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
        // Update the average rating after updating the individual rating
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
}
