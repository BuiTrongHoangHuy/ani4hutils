package site.ani4h.film.rating;

import site.ani4h.film.rating.entity.RatingRequest;

public interface RatingService {
    void upsertRating(RatingRequest request);
}
