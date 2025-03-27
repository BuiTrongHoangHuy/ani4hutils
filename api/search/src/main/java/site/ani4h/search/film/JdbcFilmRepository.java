package site.ani4h.search.film;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class JdbcFilmRepository implements FilmRepository {
    private final JdbcTemplate jdbcTemplate;
    public JdbcFilmRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Film> getFilms() {
        String sql = "SELECT * FROM `films`";
        return jdbcTemplate.query(sql,
                new BeanPropertyRowMapper<>(Film.class)
        );
    }
}
