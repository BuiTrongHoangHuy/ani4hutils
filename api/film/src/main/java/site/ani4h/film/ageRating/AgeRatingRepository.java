package site.ani4h.film.ageRating;

import org.springframework.stereotype.Repository;
import site.ani4h.film.ageRating.entity.AgeRating;
import site.ani4h.film.ageRating.entity.AgeRatingCreate;
import site.ani4h.film.ageRating.entity.AgeRatingUpdate;

import java.util.List;

@Repository
public interface AgeRatingRepository {
    void create(AgeRatingCreate create);
    List<AgeRating> getAll();
    void update(int id , AgeRatingUpdate update);
}
