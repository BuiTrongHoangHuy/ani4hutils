package site.ani4h.film.episode;

import site.ani4h.film.episode.entity.Episode;
import site.ani4h.film.episode.entity.EpisodeUpdate;

import java.util.List;

public interface EpisodeService {
    List<Episode> getEpisodesByFilmId(int filmId);
    Episode getEpisodeById(int id);
    Episode createEpisode(Episode episode);
    void updateEpisode(int id ,EpisodeUpdate episode);
    Episode getEpisodeByEpisodeNumber(int filmId, int numberEpisode);

}