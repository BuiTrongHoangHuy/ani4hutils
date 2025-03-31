package site.ani4h.film.film;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import site.ani4h.film.film.entity.Film;
import site.ani4h.film.film.entity.FilmFilter;
import site.ani4h.film.film.entity.FilmList;
import site.ani4h.shared.common.Paging;

import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcFilmRepository implements FilmRepository {
    private final JdbcTemplate jdbcTemplate;

    public JdbcFilmRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<FilmList> getFilms(Paging paging, FilmFilter filter) {
        StringBuilder sql = new StringBuilder("SELECT * FROM films WHERE 1=1");
        List<Object> params = new ArrayList<Object>();

        if (filter.getSeriesId() != null) {
            sql.append(" AND series_id = ?");
            params.add(filter.getSeriesId().getLocalId());
        }
        if (filter.getSeason() != null) {
            sql.append(" AND season = ?");
            params.add(filter.getSeason());
        }
        if (filter.getYear() != 0) {
            sql.append(" AND year = ?");
            params.add(filter.getYear());
        }
        if (filter.getAgeRatingId() != null) {
            sql.append(" AND age_rating_id = ?");
            params.add(filter.getAgeRatingId().getLocalId());
        }

        sql.append(" LIMIT ? OFFSET ?");
        params.add(paging.getPageSize());
        params.add(paging.getOffset());

        return this.jdbcTemplate.query(
                sql.toString(),
                new BeanPropertyRowMapper<>(FilmList.class),
                params.toArray()
        );
    }

    @Override
    public Film getFilmById(int id) {
        //language=MySQL
        String sql = """
                SELECT * FROM `films` WHERE id = ?
                """;
        return jdbcTemplate.queryForObject(sql,new BeanPropertyRowMapper<>(Film.class),id);
    }
}
