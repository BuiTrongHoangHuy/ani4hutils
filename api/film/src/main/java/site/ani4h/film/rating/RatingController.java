package site.ani4h.film.rating;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import site.ani4h.film.rating.entity.RatingRequest;
import site.ani4h.film.rating.entity.RatingResponse;
import site.ani4h.shared.common.Uid;

@RestController
@RequestMapping("/v1/rating")
public class RatingController {
    private final RatingService ratingService;

    public RatingController(RatingService ratingService) {
        this.ratingService = ratingService;
    }

    @PostMapping()
    public ResponseEntity<Void> upsertRating(@RequestBody RatingRequest request) {
        ratingService.upsertRating(request);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/user/{userId}/film/{filmId}")
    public ResponseEntity<RatingResponse> getRatingByUserIdAndFilmId(
            @PathVariable("userId") Uid userId,
            @PathVariable("filmId") Uid filmId) {
        RatingResponse rating = ratingService.getRatingByUserIdAndFilmId(userId.getLocalId(), filmId.getLocalId());
        if (rating == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(rating);
    }
}
