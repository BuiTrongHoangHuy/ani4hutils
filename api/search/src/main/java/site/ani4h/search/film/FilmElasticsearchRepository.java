package site.ani4h.search.film;

import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FilmElasticsearchRepository extends ElasticsearchRepository<Film, String> {
}
