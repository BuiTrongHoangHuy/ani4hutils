package site.ani4h.film.ageRating;

import org.springframework.stereotype.Service;

import java.util.List;


@Service
public interface AgeRatingService {
    void create(AgeRatingCreate create);
    List<AgeRating> getAll();
}
