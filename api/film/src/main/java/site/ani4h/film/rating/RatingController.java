package site.ani4h.film.rating;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import site.ani4h.film.rating.entity.RatingRequest;

@RestController
@RequestMapping("/v1/rating")
public class RatingController {
    private final RatingService ratingService;

    public RatingController(RatingService ratingService) {
        this.ratingService = ratingService;
    }

    @PostMapping()
    public void upsertRating(@RequestBody RatingRequest request) {
        ratingService.upsertRating(request);
    }
}
