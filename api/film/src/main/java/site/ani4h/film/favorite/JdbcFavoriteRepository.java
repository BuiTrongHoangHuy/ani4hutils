package site.ani4h.film.favorite;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class JdbcFavoriteRepository implements FavoriteRepository {
    private final JdbcTemplate jdbcTemplate;

    public JdbcFavoriteRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void addFavorite(int userId, int filmId) {
        boolean exists = isFavorite(userId, filmId);
        if (exists) {
            return;
        }

        String sql = "INSERT INTO favorites (user_id, film_id) VALUES (?, ?)";
        jdbcTemplate.update(sql, userId, filmId);
    }

    @Override
    public void removeFavorite(int userId, int filmId) {
        boolean exists = isFavorite(userId, filmId);
        if (!exists) {
            return;
        }

        String sql = "DELETE FROM favorites WHERE user_id = ? AND film_id = ?";
        jdbcTemplate.update(sql, userId, filmId);
    }

    @Override
    public boolean isFavorite(int userId, int filmId) {
        String sql = "SELECT COUNT(*) FROM favorites WHERE user_id = ? AND film_id = ?";
        int count = jdbcTemplate.queryForObject(sql, Integer.class, userId, filmId);
        return count > 0;
    }
}
