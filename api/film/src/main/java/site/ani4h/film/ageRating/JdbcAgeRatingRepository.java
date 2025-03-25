package site.ani4h.film.ageRating;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import site.ani4h.film.genre.Genre;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Objects;


@Component
public class JdbcAgeRatingRepository implements AgeRatingRepository {
    private final JdbcTemplate jdbcTemplate;

    public JdbcAgeRatingRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    @Override
    public void create(AgeRatingCreate create) {
        String sql = """
                    INSERT INTO `age_ratings` (long_name, short_name, image, description) VALUE (?,?,?,?)
                """;
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(conn -> {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, create.getLongName());
            ps.setString(2, create.getShortName());
            ps.setObject(3, create.getImage());
            ps.setString(4, create.getDescription());
            return ps;
        }, keyHolder);
        create.setId(Objects.requireNonNull(keyHolder.getKey()).intValue());
    }

    @Override
    public List<AgeRating> getAll() {
        String sql = """
                SELECT * FROM `age_ratings` ORDER BY `id` DESC;
                """;
        return jdbcTemplate.query(sql,
                new BeanPropertyRowMapper<>(AgeRating.class)
        );
    }
}
