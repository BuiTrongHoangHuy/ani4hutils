package site.ani4h.film.watch_history;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import site.ani4h.film.watch_history.entity.WatchHistoryRequest;

@Repository
public class JdbcWatchHistoryRepository implements WatchHistoryRepository {
    private final JdbcTemplate jdbcTemplate;

    public JdbcWatchHistoryRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public boolean isWatched(WatchHistoryRequest request) {
        String sql = "SELECT COUNT(*) FROM watch_history WHERE user_id = ? AND episode_id = ?";
        int count = jdbcTemplate.queryForObject(sql, new Object[]{
                request.getUserId().getLocalId(), request.getEpisodeId().getLocalId()},
                Integer.class);
        return count > 0;
    }

    @Override
    public void add(WatchHistoryRequest request) {
        String sql = "INSERT INTO watch_history (user_id, episode_id, watched_duration) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql,
                request.getUserId().getLocalId(),
                request.getEpisodeId().getLocalId(),
                request.getWatchedDuration());
    }

    @Override
    public void update(WatchHistoryRequest request) {
        String sql = "UPDATE watch_history SET watched_duration = ? WHERE user_id = ? AND episode_id = ?";
        jdbcTemplate.update(sql,
                request.getWatchedDuration(),
                request.getUserId().getLocalId(),
                request.getEpisodeId().getLocalId());
    }

    @Override
    public void delete(WatchHistoryRequest request) {
        String sql = "DELETE FROM watch_history WHERE user_id = ? AND episode_id = ?";
        jdbcTemplate.update(sql, request.getUserId().getLocalId(), request.getEpisodeId().getLocalId());
    }
}
