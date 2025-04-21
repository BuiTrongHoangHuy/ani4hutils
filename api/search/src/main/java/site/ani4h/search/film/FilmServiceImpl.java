package site.ani4h.search.film;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import site.ani4h.search.film.entity.*;
import site.ani4h.shared.common.PagingSearch;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class FilmServiceImpl implements FilmService {
    private final FilmRepository filmRepository;
    private final FilmElasticsearchRepository filmElasticsearchRepository;
    private final FilmCustomElasticRepository filmCustomElasticRepository;

    @Autowired
    public FilmServiceImpl(
            FilmRepository filmRepository,
            FilmElasticsearchRepository filmElRepository,
            FilmCustomElasticRepository filmCustomElasticRepository
    ) {
        this.filmRepository = filmRepository;
        this.filmElasticsearchRepository = filmElRepository;
        this.filmCustomElasticRepository = filmCustomElasticRepository;
    }

    @Override
    public List<Film> getFilms() {
        return filmRepository.getFilms();
    }

    @Override
    @Scheduled(cron = "0 */30 * * * *")
    public void syncFilmsToElastic() {
        log.info("Syncing films to Elasticsearch...");
        List<Film> films = filmRepository.getFilms();

        if(films.isEmpty()) {
            return;
        }

        filmElasticsearchRepository.saveAll(films);
        log.info("Synced {} films to Elasticsearch", films.size());
    }

    // Search Films
    @Override
    public SearchResponse searchFilms(SearchRequest request,PagingSearch paging) {
        if(request == null) {
            throw new IllegalArgumentException("Search request must not be null");
        }

        if(paging == null) {
            paging = new PagingSearch();
        }

        return filmCustomElasticRepository.search(request, paging);
    }
}