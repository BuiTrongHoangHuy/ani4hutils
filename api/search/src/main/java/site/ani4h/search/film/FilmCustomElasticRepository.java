package site.ani4h.search.film;

import org.springframework.stereotype.Repository;
import site.ani4h.search.film.entity.FilmResponse;
import site.ani4h.search.film.entity.SearchRequest;
import site.ani4h.search.film.entity.SearchResponse;

import java.util.List;

@Repository
public interface FilmCustomElasticRepository {
    SearchResponse search(SearchRequest searchRequest);
}
