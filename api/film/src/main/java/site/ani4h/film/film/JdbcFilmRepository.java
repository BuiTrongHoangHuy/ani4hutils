package site.ani4h.film.film;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import site.ani4h.film.film.entity.*;
import site.ani4h.shared.common.Paging;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
            sql.append(" AND year = ? ");
            params.add(filter.getYear());
        }
        if (filter.getAgeRatingId() != null) {
            sql.append(" AND age_rating_id = ?");
            params.add(filter.getAgeRatingId().getLocalId());
        }

        sql.append("  ORDER BY id DESC LIMIT ? OFFSET ?");
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
        String sql = """
            SELECT film.*, g.id as genre_id, g.name as genre_name, long_name,short_name
            FROM films film
            LEFT JOIN ani4h.age_ratings age_rating ON film.age_rating_id = age_rating.id
            LEFT JOIN film_genres fg ON film.id = fg.film_id
            LEFT JOIN genres g ON fg.genre_id = g.id
            WHERE film.id = ?
            """;

        return jdbcTemplate.query(sql, rs -> {
            Film film = null;
            Map<Integer, Film> filmMap = new HashMap<>();
            while (rs.next()) {
                int filmId = rs.getInt("id");

                if (!filmMap.containsKey(filmId)) {
                    film = new BeanPropertyRowMapper<>(Film.class).mapRow(rs, 0);
                    AgeRating ageRating = new AgeRating();
                    ageRating.setId(rs.getInt("age_rating_id"));
                    ageRating.setLongName(rs.getString("long_name"));
                    ageRating.setShortName(rs.getString("short_name"));
                    film.setAgeRating(ageRating);
                    film.setGenres(new ArrayList<Genre>());
                    filmMap.put(filmId, film);
                } else {
                    film = filmMap.get(filmId);
                }

                int genreId = rs.getInt("genre_id");
                if (genreId != 0) {
                    Genre genre = new Genre();
                    genre.setId(genreId);
                    genre.setName(rs.getString("genre_name"));
                    film.getGenres().add(genre);
                }
            }
            return film;
        }, id);
    }

    @Override
    public List<Film> getTopFilmHot(Paging paging) {
        String sql = """
                SELECT f.id, f.title, f.images, f.synopsis, f.avg_star, f.total_star,
                f.max_episodes, f.num_episodes, f.year, f.season, f.state
                FROM films f
                ORDER BY f.avg_star DESC
                LIMIT ? OFFSET ?
                """;

        return this.jdbcTemplate.query(
                sql,
                new BeanPropertyRowMapper<>(Film.class),
                paging.getPageSize(),
                paging.getOffset()
        );
    }
}
