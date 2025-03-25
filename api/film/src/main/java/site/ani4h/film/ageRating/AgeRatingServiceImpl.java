package site.ani4h.film.ageRating;

import org.springframework.stereotype.Component;
import site.ani4h.film.ageRating.entity.AgeRating;
import site.ani4h.film.ageRating.entity.AgeRatingCreate;
import site.ani4h.film.ageRating.entity.AgeRatingUpdate;

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

    @Override
    public void update(int id, AgeRatingUpdate update) {
        this.ageRatingRepository.update(id, update);
    }
}
