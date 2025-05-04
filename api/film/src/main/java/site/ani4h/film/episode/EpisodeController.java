package site.ani4h.film.episode;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import site.ani4h.film.episode.entity.Episode;
import site.ani4h.film.episode.entity.EpisodeCreate;
import site.ani4h.film.episode.entity.EpisodeUpdate;
import site.ani4h.shared.common.ApiResponse;
import site.ani4h.shared.common.Uid;

import java.util.List;

@RestController
@RequestMapping("/v1")
public class EpisodeController {
    private final EpisodeService episodeService;

    public EpisodeController(EpisodeService episodeService) {
        this.episodeService = episodeService;
    }

    @GetMapping("/film/{filmId}/episodes")
    public ResponseEntity<?> getEpisodesByFilmId(@PathVariable Uid filmId) {
        List<Episode> episodes = episodeService.getEpisodesByFilmId(filmId.getLocalId());
        return ResponseEntity.ok(ApiResponse.success(episodes));
    }

    @GetMapping("/episode/{id}")
    public ResponseEntity<?> getEpisodeById(@PathVariable Uid id) {
        Episode episode = episodeService.getEpisodeById(id.getLocalId());
        return ResponseEntity.ok(ApiResponse.success(episode));
    }

    @PostMapping("/episode")
    public ResponseEntity<?> createEpisode(@RequestBody EpisodeCreate episodeCreate) {
        Episode episode = new Episode();
        episode.setTitle(episodeCreate.getTitle());
        episode.setEpisodeNumber(episodeCreate.getEpisodeNumber());
        episode.setSynopsis(episodeCreate.getSynopsis());
        episode.setDuration(episodeCreate.getDuration());
        episode.setThumbnail(episodeCreate.getThumbnail());
        episode.setVideoUrl(episodeCreate.getVideoUrl());
        episode.setAirDate(episodeCreate.getAirDate());
        if (episodeCreate.getState() != null) {
            episode.setState(episodeCreate.getState());
        }
        episode.setFilmId(episodeCreate.getFilmId().getLocalId());

        Episode createdEpisode = episodeService.createEpisode(episode);
        return ResponseEntity.ok(ApiResponse.success(createdEpisode));
    }


}
