package site.ani4h.film.watch_history;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import site.ani4h.film.watch_history.entity.EpisodeWatchHistory;
import site.ani4h.film.watch_history.entity.WatchHistoryRequest;
import site.ani4h.shared.common.Image;
import site.ani4h.shared.common.Paging;
import site.ani4h.shared.common.Uid;

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
                SELECT e.id, e.title, e.episode_number, e.duration, e.view_count, e.film_id,
                       wh.watched_duration,
                       f.images AS filmImages, f.synopsis AS filmSynopsis, f.title AS filmTitle
                FROM episodes e
                JOIN watch_history wh ON e.id = wh.episode_id
                LEFT JOIN films f ON e.film_id = f.id
                WHERE wh.user_id = ?
                ORDER BY wh.watched_duration DESC
                LIMIT ? OFFSET ?
               \s""";

        Map<Integer, EpisodeWatchHistory> episodeMap = new LinkedHashMap<>();

        jdbcTemplate.query(sql, (rs) -> {
            EpisodeWatchHistory episode = new EpisodeWatchHistory();
            episode.setId(rs.getInt("id"));
            episode.setTitle(rs.getString("title")+ " - " + rs.getString("filmTitle"));
            episode.setEpisodeNumber(rs.getInt("episode_number"));
            episode.setSynopsis(rs.getString("filmSynopsis"));
            episode.setDuration(rs.getInt("duration"));

            episode.setViewCount(rs.getInt("view_count"));
            episode.setWatchedDuration(rs.getInt("watched_duration"));
            episode.setFilmId(rs.getInt("film_id"));
            episode.setFilmTitle(rs.getString("filmTitle"));
            try {
                String imageJson = rs.getString("filmImages");
                if(imageJson != null && !imageJson.isEmpty()){
                    List<Image> images = objectMapper.readValue(
                            imageJson,
                            new com.fasterxml.jackson.core.type.TypeReference<List<Image>>() {
                            }
                    );
                    episode.setThumbnail(images.get(0));
                }
                else {
                    episode.setThumbnail(new Image());
                }
            }catch (Exception e) {
                throw new RuntimeException(e);
            }

            episodeMap.put(episode.getId().getLocalId(), episode);
        }, request.getUserId().getLocalId(), paging.getPageSize(), paging.getOffset());

        return new ArrayList<>(episodeMap.values());
    }

    @Override
    public List<Integer> getRecentByUserId(int userId, int limit) {
        String sql = """
                SELECT e.film_id, MAX(wh.updated_at) AS last_watch_time
                FROM watch_history wh
                INNER JOIN episodes e ON wh.episode_id = e.id
                WHERE wh.user_id = ?
                GROUP BY e.film_id
                ORDER BY last_watch_time DESC
                LIMIT ?
                """;

        return jdbcTemplate.query(sql, (rs, rowNum) -> rs.getInt("film_id"), userId, limit);
    }
}
