package site.ani4h.film.ageRating;

import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AgeRatingRepository {
    void create(AgeRatingCreate create);
    List<AgeRating> getAll();
}
