package site.ani4h.search.film;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import com.fasterxml.jackson.core.type.TypeReference;
import org.springframework.stereotype.Component;
import site.ani4h.search.film.entity.Film;
import site.ani4h.search.film.entity.Genre;
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
    public List<Film> getFilms() {
        String sql = "SELECT f.id, f.title, f.images, f.synopsis, f.avg_star, f.total_star, " +
                "f.max_episodes, f.num_episodes, f.year, f.season, f.state, " +
                "a.synonyms, a.ja_name, a.en_name, " +
                "g.id AS genre_id, g.name AS genre_name " +
                "FROM films f " +
                "LEFT JOIN alternative_titles a ON f.id = a.film_id " +
                "LEFT JOIN film_genres fg ON f.id = fg.film_id " +
                "LEFT JOIN genres g ON fg.genre_id = g.id";

        Map<Integer, Film> filmMap = new HashMap<>();

        jdbcTemplate.query(sql, (rs) -> {
            int filmId = rs.getInt("id");
            Film film = filmMap.get(filmId);

            if(film == null) {
                film = new Film();
                film.setId(rs.getInt("id"));
                film.setIdSort(rs.getInt("id"));
                film.setTitle(rs.getString("title"));
                film.setGenres(new ArrayList<>());
                film.setSynopsis(rs.getString("synopsis"));
                film.setSynonyms(rs.getString("synonyms"));
                film.setJaName(rs.getString("ja_name"));
                film.setEnName(rs.getString("en_name"));
                film.setAvgStar(rs.getFloat("avg_star"));
                film.setTotalStar(rs.getInt("total_star"));
                film.setMaxEpisodes(rs.getInt("max_episodes"));
                film.setNumEpisodes(rs.getInt("num_episodes"));
                film.setYear(rs.getInt("year"));
                film.setSeason(rs.getString("season"));
                film.setState(rs.getString("state"));

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

            String genreName = rs.getString("genre_name");
            int genreId = rs.getInt("genre_id");
            if (genreName != null && !genreName.isEmpty()) {
                film.getGenres().add(new Genre(genreId, genreName));
            }
        });

        return new ArrayList<>(filmMap.values());
    }
}
