package site.ani4h.film.favorite;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import site.ani4h.film.favorite.entity.FavoriteFilm;
import site.ani4h.film.favorite.entity.Genre;
import site.ani4h.shared.common.Image;
import site.ani4h.shared.common.Paging;

import java.util.*;

@Repository
public class JdbcFavoriteRepository implements FavoriteRepository {
    private final JdbcTemplate jdbcTemplate;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public JdbcFavoriteRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void addFavorite(int userId, int filmId) {
        boolean exists = isFavorite(userId, filmId);
        if (exists) {
            return;
        }

        String sql = "INSERT INTO favorites (user_id, film_id) VALUES (?, ?)";
        jdbcTemplate.update(sql, userId, filmId);
    }

    @Override
    public void removeFavorite(int userId, int filmId) {
        boolean exists = isFavorite(userId, filmId);
        if (!exists) {
            return;
        }

        String sql = "DELETE FROM favorites WHERE user_id = ? AND film_id = ?";
        jdbcTemplate.update(sql, userId, filmId);
    }

    @Override
    public boolean isFavorite(int userId, int filmId) {
        String sql = "SELECT COUNT(*) FROM favorites WHERE user_id = ? AND film_id = ?";
        int count = jdbcTemplate.queryForObject(sql, Integer.class, userId, filmId);
        return count > 0;
    }

    @Override
    public List<FavoriteFilm> getFavoriteFilms(int userId, Paging paging) {
        String sql = """
            SELECT f.id, f.title, f.images, f.synopsis, f.avg_star, f.total_star, f.max_episodes, f.num_episodes, f.year, f.season, f.state,
                   a.synonyms, a.ja_name, a.en_name, 
                   g.id AS genre_id, g.name AS genre_name
            FROM films f
            JOIN (
                SELECT fa.film_id as film_id
                FROM favorites fa
                WHERE fa.user_id = ?
                ORDER BY fa.created_at DESC, film_id DESC 
                LIMIT ? OFFSET ?
            ) AS sub ON f.id = sub.film_id
            LEFT JOIN alternative_titles a ON f.id = a.film_id
            LEFT JOIN film_genres fg ON f.id = fg.film_id
            LEFT JOIN genres g ON fg.genre_id = g.id
            """;

        Map<Integer, FavoriteFilm> filmMap = new LinkedHashMap<>();

        jdbcTemplate.query(sql, (rs) -> {
            int filmId = rs.getInt("id");
            FavoriteFilm film = filmMap.get(filmId);

            if(film == null) {
                film = new FavoriteFilm();
                film.setId(rs.getInt("id"));
                film.setTitle(rs.getString("title"));
                film.setSynopsis(rs.getString("synopsis"));
                film.setAvgStar(rs.getFloat("avg_star"));
                film.setTotalStar(rs.getInt("total_star"));
                film.setMaxEpisodes(rs.getInt("max_episodes"));
                film.setNumEpisodes(rs.getInt("num_episodes"));
                film.setYear(rs.getInt("year"));
                film.setSeason(rs.getString("season"));
                film.setState(rs.getString("state"));

                film.setSynonyms(rs.getString("synonyms"));
                film.setJaName(rs.getString("ja_name"));
                film.setEnName(rs.getString("en_name"));

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
            }

            filmMap.put(filmId, film);

            String genreName = rs.getString("genre_name");
            int genreId = rs.getInt("genre_id");
            if (genreName != null && !genreName.isEmpty()) {
                Genre genre = new Genre();
                genre.setId(genreId);
                genre.setName(genreName);
                film.getGenres().add(genre);
            }
        }, userId, paging.getPageSize(), paging.getOffset());

        return new ArrayList<>(filmMap.values());
    }

    @Override
    public List<Integer> getRecentByUserId(int userId, int limit) {
        String sql = "SELECT film_id FROM favorites WHERE user_id = ? ORDER BY created_at DESC LIMIT ?";
        return jdbcTemplate.queryForList(sql, Integer.class, userId, limit);
    }
}
