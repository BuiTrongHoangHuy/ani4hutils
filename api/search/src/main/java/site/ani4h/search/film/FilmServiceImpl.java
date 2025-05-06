package site.ani4h.search.film;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import site.ani4h.search.film.entity.*;
import site.ani4h.search.grpc_client.favorite.FavoriteGrpcClientService;
import site.ani4h.shared.common.PagingSearch;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class FilmServiceImpl implements FilmService {
    private final FilmRepository filmRepository;
    private final FilmElasticsearchRepository filmElasticsearchRepository;
    private final FilmCustomElasticRepository filmCustomElasticRepository;
    private final FavoriteGrpcClientService favoriteGrpcClientService;

    @Autowired
    public FilmServiceImpl(
            FilmRepository filmRepository,
            FilmElasticsearchRepository filmElRepository,
            FilmCustomElasticRepository filmCustomElasticRepository,
            FavoriteGrpcClientService favoriteGrpcClientService
    ) {
        this.filmRepository = filmRepository;
        this.filmElasticsearchRepository = filmElRepository;
        this.filmCustomElasticRepository = filmCustomElasticRepository;
        this.favoriteGrpcClientService = favoriteGrpcClientService;
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

    @Override
    public SearchResponse contentBasedRecommendMLT(ContentBasedRequest request, PagingSearch paging) {
        if(request == null) {
            throw new IllegalArgumentException("Content based request must not be null");
        }

        if(paging == null) {
            paging = new PagingSearch();
        }

        List<Integer> filmIds = new ArrayList<>();
        filmIds.add(request.getFilmId().getLocalId());

        return filmCustomElasticRepository.moreLikeThisQuery(filmIds, request.getSeed(), paging);
    }

    @Override
    public SearchResponse userBasedRecommendMLT(UserBasedRequest request, PagingSearch paging) {
        List<Integer> filmIds;

        try {
            filmIds = favoriteGrpcClientService.getFilmIdRecentFavorites(request.getUserId(), 5);
            if (filmIds == null || filmIds.isEmpty()) {
                log.warn("User {} has no recent favorites. Fallback to random films.", request.getUserId());
                filmIds = randomFilmIds(5);
            }
        } catch (Exception e) {
            log.error("Failed to fetch favorites for user {}: {}. Fallback to random films.", request.getUserId(), e.getMessage());
            filmIds = randomFilmIds(5);
        }

        return filmCustomElasticRepository.moreLikeThisQuery(filmIds, request.getSeed(), paging);
    }

    @Override
    public List<Integer> randomFilmIds(int size) {
        if(size <= 0) {
            throw new IllegalArgumentException("Size must be greater than 0");
        }

        return filmCustomElasticRepository.randomFilmIds(size);
    }
}