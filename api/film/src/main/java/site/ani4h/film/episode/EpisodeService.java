package site.ani4h.film.episode;

import site.ani4h.film.episode.entity.Episode;

import java.util.List;

public interface EpisodeService {
    List<Episode> getEpisodesByFilmId(int filmId);
    Episode getEpisodeById(int id);
    Episode createEpisode(Episode episode);
}