package site.ani4h.film.watch_history;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import site.ani4h.film.watch_history.entity.EpisodeWatchHistory;
import site.ani4h.film.watch_history.entity.WatchHistoryRequest;
import site.ani4h.shared.common.Image;
import site.ani4h.shared.common.Paging;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Repository
public class JdbcWatchHistoryRepository implements WatchHistoryRepository {
    private final JdbcTemplate jdbcTemplate;
    private final ObjectMapper objectMapper = new ObjectMapper();

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

    @Override
    public List<EpisodeWatchHistory> getWatchHistoryByUserId(WatchHistoryRequest request, Paging paging) {
        String sql = """
                SELECT e.id, e.title, e.episode_number, e.synopsis, e.duration, e.thumbnail, e.view_count, e.film_id,
                       wh.watched_duration
                FROM episodes e
                JOIN watch_history wh ON e.id = wh.episode_id
                WHERE wh.user_id = ?
                ORDER BY wh.watched_duration DESC
                LIMIT ? OFFSET ?
                """;

        Map<Integer, EpisodeWatchHistory> episodeMap = new LinkedHashMap<>();

        jdbcTemplate.query(sql, (rs) -> {
            EpisodeWatchHistory episode = new EpisodeWatchHistory();
            episode.setId(rs.getInt("id"));
            episode.setTitle(rs.getString("title"));
            episode.setEpisodeNumber(rs.getInt("episode_number"));
            episode.setSynopsis(rs.getString("synopsis"));
            episode.setDuration(rs.getInt("duration"));

            episode.setViewCount(rs.getInt("view_count"));
            episode.setFilmId(rs.getInt("film_id"));
            episode.setWatchedDuration(rs.getInt("watched_duration"));

            try {
                if(rs.getString("thumbnail") != null) {
                    episode.setThumbnail(objectMapper.readValue(rs.getString("thumbnail"), Image.class));
                }
            }catch (Exception e) {
                throw new RuntimeException(e);
            }

            episodeMap.put(episode.getId().getLocalId(), episode);
        }, request.getUserId().getLocalId(), paging.getPageSize(), paging.getOffset());

        return new ArrayList<>(episodeMap.values());
    }
}
