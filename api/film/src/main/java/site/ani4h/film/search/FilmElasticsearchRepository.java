package site.ani4h.film.search;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;
import site.ani4h.film.search.entity.Film;

@Repository
public interface FilmElasticsearchRepository extends ElasticsearchRepository<Film, String> {
}
