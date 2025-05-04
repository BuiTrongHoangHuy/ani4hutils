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

}