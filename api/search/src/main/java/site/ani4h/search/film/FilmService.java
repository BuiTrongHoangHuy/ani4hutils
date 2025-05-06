package site.ani4h.search.film;

import org.springframework.stereotype.Service;
import site.ani4h.search.film.entity.*;
import site.ani4h.shared.common.PagingSearch;

import java.util.List;

@Service
public interface FilmService {
    void syncFilmsToElastic();

    SearchResponse searchFilms(SearchRequest searchRequest, PagingSearch pagingSearch);

    List<Film> getFilms();

    SearchResponse contentBasedRecommendMLT(ContentBasedRequest request, PagingSearch paging);

    SearchResponse userBasedRecommendMLT(UserBasedRequest request, PagingSearch paging);

    List<Integer> randomFilmIds(int size);
}
