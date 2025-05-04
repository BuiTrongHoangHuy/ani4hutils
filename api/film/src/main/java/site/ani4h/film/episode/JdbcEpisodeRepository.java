package site.ani4h.film.episode;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import site.ani4h.film.episode.entity.Episode;

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


    private void updateFilmEpisodeCount(int filmId) {
        String countSql = "SELECT COUNT(*) FROM episodes WHERE film_id = ?";
        int episodeCount = jdbcTemplate.queryForObject(countSql, Integer.class, filmId);

        String updateSql = "UPDATE films SET num_episodes = ? WHERE id = ?";
        jdbcTemplate.update(updateSql, episodeCount, filmId);
    }
}