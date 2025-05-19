package site.ani4h.film.rating;

import site.ani4h.film.rating.entity.RatingRequest;
import site.ani4h.film.rating.entity.RatingResponse;

public interface RatingRepository {
    void saveRating(RatingRequest request);

    void updateRating(RatingRequest request);

    boolean isExistRating(RatingRequest request);

    void updateAverageRating(int filmId);

    RatingResponse getRatingByUserIdAndFilmId(int userId, int filmId);
}
