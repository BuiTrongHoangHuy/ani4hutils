package site.ani4h.film.episode;

import org.springframework.beans.factory.annotation.Value;
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

    @Value("${cdn.video-url}")
    private String cdnVideoUrl;

    public EpisodeController(EpisodeService episodeService) {
        this.episodeService = episodeService;
    }

    @GetMapping("/film/{filmId}/episodes")
    public ResponseEntity<?> getEpisodesByFilmId(@PathVariable Uid filmId) {
        List<Episode> episodes = episodeService.getEpisodesByFilmId(filmId.getLocalId());
        for (Episode episode : episodes) {
            if (episode.getVideoUrl() != null && !episode.getVideoUrl().isEmpty()) {
                episode.setVideoUrl(cdnVideoUrl + episode.getVideoUrl());
            }
        }
        return ResponseEntity.ok(ApiResponse.success(episodes));
    }

    @GetMapping("/episode/{id}")
    public ResponseEntity<?> getEpisodeById(@PathVariable Uid id) {
        Episode episode = episodeService.getEpisodeById(id.getLocalId());
        if (episode.getVideoUrl() != null && !episode.getVideoUrl().isEmpty()) {
            episode.setVideoUrl(cdnVideoUrl + episode.getVideoUrl());
        }
        return ResponseEntity.ok(ApiResponse.success(episode));
    }

    @GetMapping("/film/{filmId}/episode/{numberEpisode}")
    public ResponseEntity<?> getEpisodeByEpisodeNumber(@PathVariable Uid filmId , @PathVariable int numberEpisode) {
        Episode episode = episodeService.getEpisodeByEpisodeNumber( filmId.getLocalId(),numberEpisode);
        if (episode.getVideoUrl() != null && !episode.getVideoUrl().isEmpty()) {
            episode.setVideoUrl(cdnVideoUrl + episode.getVideoUrl());
        }
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
        episode.setVideoUrl(episodeCreate.getVideoUrl()+"/master.m3u8");
        episode.setAirDate(episodeCreate.getAirDate());
        if (episodeCreate.getState() != null) {
            episode.setState(episodeCreate.getState());
        }
        episode.setFilmId(episodeCreate.getFilmId().getLocalId());

        Episode createdEpisode = episodeService.createEpisode(episode);
        return ResponseEntity.ok(ApiResponse.success(createdEpisode));
    }

    @PutMapping("/episode/{id}")
    public ResponseEntity<?> updateEpisode(@PathVariable Uid id, @RequestBody EpisodeUpdate episodeUpdate) {
        episodeService.updateEpisode(id.getLocalId(), episodeUpdate);
        return ResponseEntity.ok(true);
    }

}
