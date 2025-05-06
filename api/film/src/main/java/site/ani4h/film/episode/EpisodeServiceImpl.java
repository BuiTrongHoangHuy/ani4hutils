package site.ani4h.film.episode;

import org.springframework.stereotype.Service;
import site.ani4h.film.episode.entity.Episode;
import site.ani4h.film.episode.entity.EpisodeUpdate;

import java.util.List;

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

}