package site.ani4h.search.film;

import org.springframework.stereotype.Repository;
import site.ani4h.search.film.entity.ContentBasedRequest;
import site.ani4h.search.film.entity.SearchRequest;
import site.ani4h.search.film.entity.SearchResponse;
import site.ani4h.shared.common.PagingSearch;

import java.util.List;

@Repository
public interface FilmCustomElasticRepository {
    SearchResponse search(SearchRequest searchRequest, PagingSearch paging);
    SearchResponse moreLikeThisQuery(List<Integer> filmIds,int seed, PagingSearch paging);
    List<Integer> randomFilmIds(int size);
}
