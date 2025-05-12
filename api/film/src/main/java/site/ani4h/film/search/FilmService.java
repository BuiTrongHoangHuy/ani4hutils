package site.ani4h.film.search;

import org.springframework.stereotype.Service;
import site.ani4h.film.search.entity.*;
import site.ani4h.shared.common.PagingSearch;

import java.util.List;

@Service
public interface FilmService {
    void syncFilmsToElastic();

    SearchResponse searchFilms(SearchRequest searchRequest, PagingSearch pagingSearch);

    List<Film> getFilms();

    SearchResponse contentBasedRecommendMLT(ContentBasedRequest request, PagingSearch paging);

    SearchResponse userFavoriteRecommendMLT(UserBasedRequest request, PagingSearch paging);

    SearchResponse userHistoryRecommendMLT(UserBasedRequest request, PagingSearch paging);

    List<Integer> randomFilmIds(int size);
}
