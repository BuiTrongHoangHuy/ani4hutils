package site.ani4h.search.film;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;
import site.ani4h.search.film.entity.Film;

@Repository
public interface FilmElasticsearchRepository extends ElasticsearchRepository<Film, String> {
}
