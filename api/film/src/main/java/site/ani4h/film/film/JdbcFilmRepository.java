package site.ani4h.film.film;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import site.ani4h.film.film.entity.*;
import site.ani4h.shared.common.Image;
import site.ani4h.shared.common.Images;
import site.ani4h.shared.common.Paging;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class JdbcFilmRepository implements FilmRepository {
    private final JdbcTemplate jdbcTemplate;
    private final ObjectMapper objectMapper = new ObjectMapper();

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
        Film film = jdbcTemplate.queryForObject("""
        SELECT f.*, ar.long_name, ar.short_name
        FROM films f
        LEFT JOIN ani4h.age_ratings ar ON f.age_rating_id = ar.id
        WHERE f.id = ?
    """, (rs, rowNum) -> {
            Film result = new BeanPropertyRowMapper<>(Film.class).mapRow(rs, rowNum);
            AgeRating ageRating = new AgeRating();
            ageRating.setId(rs.getInt("age_rating_id"));
            ageRating.setLongName(rs.getString("long_name"));
            ageRating.setShortName(rs.getString("short_name"));
            result.setAgeRating(ageRating);
            return result;
        }, id);

        if (film == null) return null;

        List<Genre> genres = jdbcTemplate.query("""
        SELECT g.id, g.name
        FROM film_genres fg
        JOIN genres g ON fg.genre_id = g.id
        WHERE fg.film_id = ?
    """, (rs, rowNum) -> {
            Genre genre = new Genre();
            genre.setId(rs.getInt("id"));
            genre.setName(rs.getString("name"));
            return genre;
        }, id);
        film.setGenres(genres);

        List<FilmCharacter> characters = jdbcTemplate.query("""
        SELECT fc.id, fc.name, fc.role, fc.image
        FROM film_film_characters ffc
        JOIN film_characters fc ON ffc.film_character_id = fc.id
        WHERE ffc.film_id = ?
    """, (rs, rowNum) -> {
            FilmCharacter character = new FilmCharacter();
            character.setId(rs.getInt("id"));
            character.setName(rs.getString("name"));
            character.setRole(rs.getString("role"));

            String imageJson = rs.getString("image");
            if (imageJson != null && !imageJson.isEmpty()) {
                try {
                    character.setImage(objectMapper.readValue(imageJson, new TypeReference<>() {}));
                } catch (Exception e) {
                    throw new RuntimeException("Failed to parse character image", e);
                }
            }

            List<Actor> actors = jdbcTemplate.query("""
            SELECT a.id, a.name, a.language, a.image
            FROM film_character_actors fca
            JOIN actors a ON fca.actor_id = a.id
            WHERE fca.film_character_id = ?
        """, (ars, i) -> {
                Actor actor = new Actor();
                actor.setId(ars.getInt("id"));
                actor.setName(ars.getString("name"));
                actor.setLanguage(ars.getString("language"));
                String actorImageJson = ars.getString("image");
                if (actorImageJson != null && !actorImageJson.isEmpty()) {
                    try {
                        actor.setImage(objectMapper.readValue(actorImageJson, new TypeReference<>() {}));
                    } catch (Exception e) {
                        throw new RuntimeException("Failed to parse actor image", e);
                    }
                }
                return actor;
            }, character.getId().getLocalId());

            character.setActors(actors);
            return character;
        }, id);
        film.setCharacters(characters);

        List<Producer> producers = jdbcTemplate.query("""
        SELECT p.id, p.name, p.image, p.description
        FROM film_producers fp
        JOIN producers p ON fp.producer_id = p.id
        WHERE fp.film_id = ?
    """, (rs, rowNum) -> {
            Producer producer = new Producer();
            producer.setId(rs.getInt("id"));
            producer.setName(rs.getString("name"));
            producer.setDescription(rs.getString("description"));
            String imageJson = rs.getString("image");
            if (imageJson != null && !imageJson.isEmpty()) {
                try {
                    producer.setImage(objectMapper.readValue(imageJson, new TypeReference<>() {}));
                } catch (Exception e) {
                    throw new RuntimeException("Failed to parse producer image", e);
                }
            }
            return producer;
        }, id);
        film.setProducers(producers);

        List<Studio> studios = jdbcTemplate.query("""
        SELECT s.id, s.name, s.image, s.description
        FROM film_studios fs
        JOIN studios s ON fs.studio_id = s.id
        WHERE fs.film_id = ?
    """, (rs, rowNum) -> {
            Studio studio = new Studio();
            studio.setId(rs.getInt("id"));
            studio.setName(rs.getString("name"));
            studio.setDescription(rs.getString("description"));
            String imageJson = rs.getString("image");
            if (imageJson != null && !imageJson.isEmpty()) {
                try {
                    studio.setImage(objectMapper.readValue(imageJson, new TypeReference<>() {}));
                } catch (Exception e) {
                    throw new RuntimeException("Failed to parse studio image", e);
                }
            }
            return studio;
        }, id);
        film.setStudios(studios);

        /*Integer totalViews = jdbcTemplate.queryForObject("""
        SELECT COALESCE(SUM(view_count), 0) FROM episodes WHERE film_id = ?
    """, Integer.class, id);
        film.setView(totalViews);*/

        return film;
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
