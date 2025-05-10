package site.ani4h.film.rating;

import org.springframework.stereotype.Service;
import site.ani4h.film.rating.entity.RatingRequest;

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
}
