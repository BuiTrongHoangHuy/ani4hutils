package site.ani4h.search.film;
import co.elastic.clients.elasticsearch._types.query_dsl.MatchQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch._types.query_dsl.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.client.elc.NativeQueryBuilder;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FilmService {
    private final FilmRepository filmRepository;
    private final FilmElasticsearchRepository filmElasticsearchRepository;
    private final ElasticsearchOperations elasticsearchOperations;

    @Autowired
    public FilmService(
            FilmRepository filmRepository,
            FilmElasticsearchRepository filmElRepository,
            ElasticsearchOperations elasticsearchOperations
    ) {
        this.filmRepository = filmRepository;
        this.filmElasticsearchRepository = filmElRepository;
        this.elasticsearchOperations = elasticsearchOperations;
    }

    public List<Film> getFilms() {
        return filmRepository.getFilms();
    }

    public void syncFilmsToElastic() {
        List<Film> films = filmRepository.getFilms();

        if(films.isEmpty()) {
            return;
        }

        filmElasticsearchRepository.saveAll(films);
    }

    public List<Film> getFilmsByTitle(String title) {
        NativeQuery searchQuery = new NativeQueryBuilder()
                .withQuery(QueryBuilders.match(m->m.field("title").query(title)))
                .build();

        SearchHits<Film> searchHits = elasticsearchOperations.search(searchQuery, Film.class);
        return searchHits.stream()
                .map(hit -> hit.getContent())
                .collect(Collectors.toList());
    }
}
