package site.ani4h.film.episode;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import site.ani4h.film.episode.entity.Episode;
import site.ani4h.film.episode.entity.EpisodeUpdate;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Component
public class JdbcEpisodeRepository implements EpisodeRepository {
    private final JdbcTemplate jdbcTemplate;

    public JdbcEpisodeRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Episode> getEpisodesByFilmId(int filmId) {
        String sql = "SELECT * FROM episodes WHERE film_id = ? ORDER BY episode_number";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Episode.class), filmId);
    }

    @Override
    public Episode getEpisodeById(int id) {
        String sql = "SELECT * FROM episodes WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(Episode.class), id);
    }

    @Override
    public Episode createEpisode(Episode episode) {
        String sql = "INSERT INTO episodes (title, episode_number, synopsis, duration, thumbnail, video_url, " +
                "air_date, state, film_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, episode.getTitle());
            ps.setInt(2, episode.getEpisodeNumber());
            ps.setString(3, episode.getSynopsis());
            ps.setInt(4, episode.getDuration());
            ps.setString(5, episode.getThumbnail() != null ? episode.getThumbnail().toString() : null);
            ps.setString(6, episode.getVideoUrl().substring(episode.getVideoUrl().indexOf("/film")));
            ps.setTimestamp(7, episode.getAirDate() != null ? Timestamp.valueOf(episode.getAirDate()) : null);
            ps.setString(8, episode.getState() != null ? String.valueOf(episode.getState()) : null);
            ps.setInt(9, episode.getFilmId().getLocalId());
            return ps;
        }, keyHolder);

        int id = Objects.requireNonNull(keyHolder.getKey()).intValue();
        episode.setId(id);

        updateFilmEpisodeCount(episode.getFilmId().getLocalId());

        return episode;
    }

    @Override
    public void updateEpisode(int id, EpisodeUpdate episode) {
        /*String sql = "UPDATE episodes SET title = ?, episode_number = ?, synopsis = ?, duration = ?, " +
                "thumbnail = ?, video_url = ?, air_date = ?, state = ? WHERE id = ?";*/
        String sql = """
                UPDATE `episodes`
                SET title = COALESCE(?,title),
                    episode_number = COALESCE(?,episode_number),
                    synopsis = COALESCE(?,synopsis),
                    duration = COALESCE(?,duration),
                    thumbnail = ?,
                    video_url = COALESCE(?,video_url),
                    air_date = COALESCE(?,air_date),
                    state = COALESCE(?,state)
                WHERE id = ?
                """;
        try {

            var objectMapper = new ObjectMapper().writer().writeValueAsString(episode.getThumbnail());
            jdbcTemplate.update(sql,
                    episode.getTitle(),
                    episode.getEpisodeNumber(),
                    episode.getSynopsis(),
                    episode.getDuration(),
                    episode.getThumbnail() == null ? null : objectMapper,
                    episode.getVideoUrl()== null ? null : episode.getVideoUrl().substring(episode.getVideoUrl().indexOf("/film"))+"/master.m3u8",
                    episode.getAirDate() != null ? Timestamp.valueOf(episode.getAirDate()) : null,
                    episode.getState() != null ? String.valueOf(episode.getState())  : null,
                    id);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public Episode getEpisodeByEpisodeNumber(int filmId,int numberEpisode) {
        String sql = "SELECT * FROM episodes WHERE film_id = ? AND episode_number = ?";
        return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(Episode.class), filmId, numberEpisode);
    }
    private void updateFilmEpisodeCount(int filmId) {
        String countSql = "SELECT COUNT(*) FROM episodes WHERE film_id = ?";
        int episodeCount = jdbcTemplate.queryForObject(countSql, Integer.class, filmId);

        String updateSql = "UPDATE films SET num_episodes = ? WHERE id = ?";
        jdbcTemplate.update(updateSql, episodeCount, filmId);
    }

    @Override
    public int getWatchedDuration(int userId, int episodeId) {
        String sql = "SELECT watched_duration FROM watch_history WHERE user_id = ? AND episode_id = ?";
        Integer watchedDuration = jdbcTemplate.queryForObject(sql, Integer.class, userId, episodeId);
        return watchedDuration != null ? watchedDuration : 0;
    }
}