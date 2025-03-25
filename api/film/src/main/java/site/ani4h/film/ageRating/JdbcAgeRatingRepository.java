package site.ani4h.film.ageRating;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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
        try {
            var objectMapper = new ObjectMapper().writer().writeValueAsString(create.getImage());
            KeyHolder keyHolder = new GeneratedKeyHolder();
            System.out.println("objectMapper:"+ objectMapper);
            jdbcTemplate.update(conn -> {
                PreparedStatement ps = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
                ps.setString(1, create.getLongName());
                ps.setString(2, create.getShortName());
                if (create.getImage() == null) {
                    ps.setObject(3,null);
                } else {
                    ps.setString(3, objectMapper);
                }
                ps.setString(4, create.getDescription());
                return ps;
            }, keyHolder);
            create.setId(Objects.requireNonNull(keyHolder.getKey()).intValue());
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
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

    @Override
    public void update(int id, AgeRatingUpdate update) {
        String sql = """
                    UPDATE `age_ratings`
                    SET
                        `long_name` = COALESCE(?, `long_name`),
                        `short_name` = COALESCE(?,`short_name`),
                        `image` = COALESCE(?,`image`),
                        `min_age_to_watch` = COALESCE(?,`min_age_to_watch`),
                        `description` = COALESCE(?,`description`)
                    WHERE `id` = ?
                """;
        try {
            var objectMapper = new ObjectMapper().writer().writeValueAsString(update.getImage());
            jdbcTemplate.update(sql,
                    update.getLongName(),
                    update.getShortName(),
                    update.getImage() == null ? null : objectMapper,
                    update.getMinAgeToWatch(),
                    update.getDescription(),
                    id);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
