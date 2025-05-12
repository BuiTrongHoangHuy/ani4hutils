package site.ani4h.film.episode;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import site.ani4h.film.episode.entity.Episode;
import site.ani4h.film.episode.entity.EpisodeRequest;
import site.ani4h.film.episode.entity.EpisodeUpdate;
import site.ani4h.shared.common.Uid;

import java.util.List;

@Slf4j
@Service
public class EpisodeServiceImpl implements EpisodeService {
    private final EpisodeRepository episodeRepository;

    public EpisodeServiceImpl(EpisodeRepository episodeRepository) {
        this.episodeRepository = episodeRepository;
    }

    @Override
    public List<Episode> getEpisodesByFilmId(int filmId) {
        return episodeRepository.getEpisodesByFilmId(filmId);
    }

    @Override
    public Episode getEpisodeById(int id) {
        return episodeRepository.getEpisodeById(id);
    }

    @Override
    public Episode createEpisode(Episode episode) {
        return episodeRepository.createEpisode(episode);
    }

    @Override
    public void updateEpisode(int id,EpisodeUpdate episode) {
         episodeRepository.updateEpisode(id, episode);
    }

    @Override
    public Episode getEpisodeByEpisodeNumber(EpisodeRequest request) {

        int filmId = new Uid(request.getFilmId()).getLocalId();
        int numberEpisode = request.getNumberEpisode();
        Episode episode = episodeRepository.getEpisodeByEpisodeNumber( filmId, numberEpisode);

        // try get watched duration
        try {
            int userId = new Uid(request.getUserId()).getLocalId();

            int watchedDuration = episodeRepository.getWatchedDuration(userId, episode.getId().getLocalId());

            episode.setWatchedDuration(watchedDuration);
        } catch (Exception e) {
            log.error("Get watched duration error", e);
        }

        if(episode.getWatchedDuration() == null || episode.getWatchedDuration() == 0) {
            episode.setWatchedDuration(0);
        }

        return episode;
    }

}