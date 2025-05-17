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
        String sql = """
            SELECT film.*, g.id as genre_id, g.name as genre_name, long_name, short_name,
                   fc.id as character_id, fc.name as character_name, fc.role as character_role, fc.image as character_image,
                   a.id as actor_id, a.name as actor_name, a.language as actor_language, a.image as actor_image,
                   p.id as producer_id, p.name as producer_name, p.image as producer_image, p.description as producer_description,
                   s.id as studio_id, s.name as studio_name, s.image as studio_image, s.description as studio_description
            FROM films film
            LEFT JOIN ani4h.age_ratings age_rating ON film.age_rating_id = age_rating.id
            LEFT JOIN film_genres fg ON film.id = fg.film_id
            LEFT JOIN genres g ON fg.genre_id = g.id
            LEFT JOIN film_film_characters ffc ON film.id = ffc.film_id
            LEFT JOIN film_characters fc ON ffc.film_character_id = fc.id
            LEFT JOIN film_character_actors fca ON fc.id = fca.film_character_id
            LEFT JOIN actors a ON fca.actor_id = a.id
            LEFT JOIN film_producers fp ON film.id = fp.film_id
            LEFT JOIN producers p ON fp.producer_id = p.id
            LEFT JOIN film_studios fs ON film.id = fs.film_id
            LEFT JOIN studios s ON fs.studio_id = s.id
            WHERE film.id = ?
            """;

        Film film = jdbcTemplate.query(sql, rs -> {
            Film result = null;
            Map<Integer, Film> filmMap = new HashMap<>();
            Map<Integer, FilmCharacter> characterMap = new HashMap<>();
            Map<Integer, Actor> actorMap = new HashMap<>();
            Map<Integer, Producer> producerMap = new HashMap<>();
            Map<Integer, Studio> studioMap = new HashMap<>();

            while (rs.next()) {
                int filmId = rs.getInt("id");

                if (!filmMap.containsKey(filmId)) {
                    result = new BeanPropertyRowMapper<>(Film.class).mapRow(rs, 0);
                    AgeRating ageRating = new AgeRating();
                    ageRating.setId(rs.getInt("age_rating_id"));
                    ageRating.setLongName(rs.getString("long_name"));
                    ageRating.setShortName(rs.getString("short_name"));
                    result.setAgeRating(ageRating);
                    result.setGenres(new ArrayList<Genre>());
                    result.setCharacters(new ArrayList<FilmCharacter>());
                    result.setProducers(new ArrayList<Producer>());
                    result.setStudios(new ArrayList<Studio>());
                    filmMap.put(filmId, result);
                } else {
                    result = filmMap.get(filmId);
                }

                int genreId = rs.getInt("genre_id");
                if (genreId != 0) {
                    boolean genreExists = false;
                    for (Genre existingGenre : result.getGenres()) {
                        if (existingGenre.getId().getLocalId() == genreId) {
                            genreExists = true;
                            break;
                        }
                    }

                    if (!genreExists) {
                        Genre genre = new Genre();
                        genre.setId(genreId);
                        genre.setName(rs.getString("genre_name"));
                        result.getGenres().add(genre);
                    }
                }

                int characterId = rs.getInt("character_id");
                if (characterId != 0) {
                    FilmCharacter character;
                    if (!characterMap.containsKey(characterId)) {
                        character = new FilmCharacter();
                        character.setId(characterId);
                        character.setName(rs.getString("character_name"));
                        character.setRole(rs.getString("character_role"));
                        String characterImageJson = rs.getString("character_image");
                        if (characterImageJson != null && !characterImageJson.isEmpty()) {
                            try{
                                Image image = objectMapper.readValue(
                                        characterImageJson,
                                        new TypeReference<Image>() {}
                                );
                                character.setImage(image);
                            }
                            catch (Exception e) {
                                throw new RuntimeException("Failed to parse images JSON", e);
                            }

                        }
                        character.setActors(new ArrayList<Actor>());
                        characterMap.put(characterId, character);
                        result.getCharacters().add(character);
                    } else {
                        character = characterMap.get(characterId);
                    }

                    int actorId = rs.getInt("actor_id");
                    if (actorId != 0) {
                        if (!actorMap.containsKey(actorId)) {
                            Actor actor = new Actor();
                            actor.setId(actorId);
                            actor.setName(rs.getString("actor_name"));
                            actor.setLanguage(rs.getString("actor_language"));
                            String actorImageJson = rs.getString("actor_image");
                            if (actorImageJson != null && !actorImageJson.isEmpty()) {
                                try{
                                    Image image = objectMapper.readValue(
                                            actorImageJson,
                                            new TypeReference<Image>() {}
                                    );
                                    actor.setImage(image);
                                }
                                catch (Exception e) {
                                    throw new RuntimeException("Failed to parse images JSON", e);
                                }
                                actor.setImage(new Image(actorImageJson));
                            }
                            actorMap.put(actorId, actor);
                            character.getActors().add(actor);
                        }
                    }
                }

                int producerId = rs.getInt("producer_id");
                if (producerId != 0) {
                    if (!producerMap.containsKey(producerId)) {
                        Producer producer = new Producer();
                        producer.setId(producerId);
                        producer.setName(rs.getString("producer_name"));
                        producer.setDescription(rs.getString("producer_description"));
                        String producerImageJson = rs.getString("producer_image");
                        if (producerImageJson != null && !producerImageJson.isEmpty()) {
                            try {
                                Image image = objectMapper.readValue(
                                        producerImageJson,
                                        new TypeReference<Image>() {}
                                );
                                producer.setImage(image);
                            }
                            catch (Exception e) {
                                throw new RuntimeException("Failed to parse producer image JSON", e);
                            }
                        }
                        producerMap.put(producerId, producer);
                        result.getProducers().add(producer);
                    }
                }

                int studioId = rs.getInt("studio_id");
                if (studioId != 0) {
                    if (!studioMap.containsKey(studioId)) {
                        Studio studio = new Studio();
                        studio.setId(studioId);
                        studio.setName(rs.getString("studio_name"));
                        studio.setDescription(rs.getString("studio_description"));
                        String studioImageJson = rs.getString("studio_image");
                        if (studioImageJson != null && !studioImageJson.isEmpty()) {
                            try {
                                Image image = objectMapper.readValue(
                                        studioImageJson,
                                        new TypeReference<Image>() {}
                                );
                                studio.setImage(image);
                            }
                            catch (Exception e) {
                                throw new RuntimeException("Failed to parse studio image JSON", e);
                            }
                        }
                        studioMap.put(studioId, studio);
                        result.getStudios().add(studio);
                    }
                }
            }
            return result;
        }, id);

        if (film != null) {
            String viewCountSql = "SELECT COALESCE(SUM(view_count), 0) as total_views FROM episodes WHERE film_id = ?";
            Integer totalViews = jdbcTemplate.queryForObject(viewCountSql, Integer.class, id);
            film.setView(totalViews);
        }

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
