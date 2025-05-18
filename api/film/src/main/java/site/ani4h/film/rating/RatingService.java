package site.ani4h.film.rating;

import site.ani4h.film.rating.entity.RatingRequest;
import site.ani4h.film.rating.entity.RatingResponse;

public interface RatingService {
    void upsertRating(RatingRequest request);

    RatingResponse getRatingByUserIdAndFilmId(int userId, int filmId);
}
