package site.ani4h.film.ageRating;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AgeRatingServiceImpl implements AgeRatingService {
    private final AgeRatingRepository ageRatingRepository;

    public AgeRatingServiceImpl(AgeRatingRepository ageRatingRepository) {
        this.ageRatingRepository = ageRatingRepository;
    }

    @Override
    public void create(AgeRatingCreate create) {
        this.ageRatingRepository.create(create);
    }

    @Override
    public List<AgeRating> getAll() {
        return this.ageRatingRepository.getAll();
    }
}
