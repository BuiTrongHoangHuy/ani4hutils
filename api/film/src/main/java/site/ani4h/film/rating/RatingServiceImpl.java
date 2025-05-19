package site.ani4h.film.rating;

import org.springframework.stereotype.Service;
import site.ani4h.film.rating.entity.RatingRequest;
import site.ani4h.film.rating.entity.RatingResponse;

@Service
public class RatingServiceImpl implements RatingService {
    private final RatingRepository ratingRepository;

    public RatingServiceImpl(RatingRepository ratingRepository) {
        this.ratingRepository = ratingRepository;
    }

    @Override
    public void upsertRating(RatingRequest request) {
        if (ratingRepository.isExistRating(request)) {
            ratingRepository.updateRating(request);
        } else {
            ratingRepository.saveRating(request);
        }
    }

    @Override
    public RatingResponse getRatingByUserIdAndFilmId(int userId, int filmId) {
        return ratingRepository.getRatingByUserIdAndFilmId(userId, filmId);
    }
}
