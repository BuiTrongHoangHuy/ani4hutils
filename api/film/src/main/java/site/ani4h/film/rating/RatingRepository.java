package site.ani4h.film.rating;

import site.ani4h.film.rating.entity.RatingRequest;

public interface RatingRepository {
    void saveRating(RatingRequest request);

    void updateRating(RatingRequest request);

    boolean isExistRating(RatingRequest request);

    void updateAverageRating(int filmId);
}
