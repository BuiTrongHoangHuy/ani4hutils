package site.ani4h.film.ageRating;

import org.springframework.stereotype.Service;
import site.ani4h.film.ageRating.entity.AgeRating;
import site.ani4h.film.ageRating.entity.AgeRatingCreate;
import site.ani4h.film.ageRating.entity.AgeRatingUpdate;

import java.util.List;


@Service
public interface AgeRatingService {
    void create(AgeRatingCreate create);
    List<AgeRating> getAll();
    void update(int id, AgeRatingUpdate update);
}
