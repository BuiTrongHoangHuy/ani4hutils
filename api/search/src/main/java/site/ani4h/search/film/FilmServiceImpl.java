package site.ani4h.search.film;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import site.ani4h.search.film.entity.*;
import site.ani4h.search.grpc_client.favorite.FavoriteGrpcClientService;
import site.ani4h.search.grpc_client.history.HistoryGrpcClientService;
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
    private final HistoryGrpcClientService historyGrpcClientService;

    @Autowired
    public FilmServiceImpl(
            FilmRepository filmRepository,
            FilmElasticsearchRepository filmElRepository,
            FilmCustomElasticRepository filmCustomElasticRepository,
            FavoriteGrpcClientService favoriteGrpcClientService,
            HistoryGrpcClientService historyGrpcClientService
    ) {
        this.filmRepository = filmRepository;
        this.filmElasticsearchRepository = filmElRepository;
        this.filmCustomElasticRepository = filmCustomElasticRepository;
        this.favoriteGrpcClientService = favoriteGrpcClientService;
        this.historyGrpcClientService = historyGrpcClientService;
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
    public SearchResponse userFavoriteRecommendMLT(UserBasedRequest request, PagingSearch paging) {
        List<Integer> filmIds;
        int count = 10;

        try {
            filmIds = favoriteGrpcClientService.getFilmIdRecentFavorites(request.getUserId().getLocalId(), count);
            if (filmIds == null || filmIds.isEmpty()) {
                log.warn("User {} has no recent favorites. Fallback to random films.", request.getUserId());
                filmIds = randomFilmIds(count);
            }
        } catch (Exception e) {
            log.error("Failed to fetch favorites for user {}: {}. Fallback to random films.", request.getUserId(), e.getMessage());
            filmIds = randomFilmIds(count);
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

    @Override
    public SearchResponse userHistoryRecommendMLT(UserBasedRequest request, PagingSearch paging) {
        List<Integer> filmIds;
        int count = 10;

        try {
            filmIds = historyGrpcClientService.getListFilmIdRecentHistory(request.getUserId().getLocalId(), count);
            if (filmIds == null || filmIds.isEmpty()) {
                log.warn("User {} has no recent histories. Fallback to random films.", request.getUserId());
                filmIds = randomFilmIds(count);
            }
        } catch (Exception e) {
            log.error("Failed to fetch histories for user {}: {}. Fallback to random films.", request.getUserId(), e.getMessage());
            filmIds = randomFilmIds(count);
        }

        return filmCustomElasticRepository.moreLikeThisQuery(filmIds, request.getSeed(), paging);
    }
}