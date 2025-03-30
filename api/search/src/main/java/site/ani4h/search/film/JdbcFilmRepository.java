package site.ani4h.search.film;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import com.fasterxml.jackson.core.type.TypeReference;
import org.springframework.stereotype.Component;
import site.ani4h.shared.common.Image;

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
    public List<FilmModel> getFilms() {
        String sql = "SELECT f.id, f.title, f.images, g.name " +
                "FROM films f " +
                "LEFT JOIN film_genres fg ON f.id = fg.film_id " +
                "LEFT JOIN genres g ON fg.genre_id = g.id";

        Map<Integer, FilmModel> filmMap = new HashMap<>();

        jdbcTemplate.query(sql, (rs) -> {
            int filmId = rs.getInt("id");
            FilmModel film = filmMap.get(filmId);

            if(film == null) {
                film = new FilmModel();
                film.setId(rs.getInt("id"));
                film.setTitle(rs.getString("title"));
                film.setGenres(new ArrayList<>());

                try {
                    String imagesJson = rs.getString("images");
                    if (imagesJson != null && !imagesJson.isEmpty()) {
                        List<Image> images = objectMapper.readValue(
                                imagesJson,
                                new TypeReference<List<Image>>() {
                                }
                        );
                        film.setImages(images);
                    } else {
                        film.setImages(new ArrayList<>());
                    }
                } catch (Exception e) {
                    throw new RuntimeException("Failed to parse images JSON", e);
                }

                filmMap.put(filmId, film);
            }

            String genreName = rs.getString("name");
            if (genreName != null) {
                film.getGenres().add(genreName);
            }
        });

        return new ArrayList<>(filmMap.values());
    }
}
