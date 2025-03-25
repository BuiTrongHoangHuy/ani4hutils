package site.ani4h.film.ageRating;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import site.ani4h.film.ageRating.entity.AgeRatingCreate;
import site.ani4h.film.ageRating.entity.AgeRatingUpdate;
import site.ani4h.shared.common.Uid;

@RestController
@RequestMapping("v1/age-rating")
public class AgeRatingController {
    private final AgeRatingService ageRatingService;

    public AgeRatingController(AgeRatingService ageRatingService) {
        this.ageRatingService = ageRatingService;
    }

    @PostMapping()
    public ResponseEntity<?> create(@RequestBody AgeRatingCreate ageRating) {
        this.ageRatingService.create(ageRating);
        return ResponseEntity.ok(ageRating.getId());
    }

    @GetMapping()
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok(ageRatingService.getAll());
    }

    @PatchMapping("{id}")
    public ResponseEntity<?> update(@PathVariable Uid id, @RequestBody AgeRatingUpdate ageRatingUpdate) {
        this.ageRatingService.update(id.getLocalId(), ageRatingUpdate);
        return ResponseEntity.ok(true);
    }
}
