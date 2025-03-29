package site.ani4h.search.film;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch._types.query_dsl.QueryBuilders;
import co.elastic.clients.elasticsearch.core.search.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.client.elc.NativeQueryBuilder;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.Criteria;
import org.springframework.data.elasticsearch.core.query.CriteriaQuery;
import org.springframework.data.elasticsearch.core.query.StringQuery;
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

        for (Film film : films) {
            film.setKeyword(film.getTitle());
        }

        filmElasticsearchRepository.saveAll(films);
    }

    // Search for films by title
    public List<Film> getFilmsByTitle(String title) {
        NativeQuery searchQuery = new NativeQueryBuilder()
                .withQuery(QueryBuilders.match(m->m.field("title").query(title).fuzziness("AUTO")))
                .build();

        System.out.println("Query: " + searchQuery.getQuery());
        SearchHits<Film> searchHits = elasticsearchOperations.search(searchQuery, Film.class);
        System.out.println("Search hits: " + searchHits.getTotalHits());
        return searchHits.stream()
                .map(SearchHit::getContent)
                .collect(Collectors.toList());
    }

    // suggest keyword when user typing with prefix, completion
    public List<String> getFilmsByTitleSuggest(String keyword) {
        String formatKeyword = keyword.toLowerCase();

        NativeQuery searchQuery = new NativeQueryBuilder()
                .withQuery(QueryBuilders.matchPhrasePrefix(m->m.field("keyword").query(formatKeyword)))
                .withPageable(PageRequest.of(0, 10))
                .build();

        System.out.println("Query: " + searchQuery.getQuery());
        SearchHits<Film> searchHits = elasticsearchOperations.search(searchQuery, Film.class);
        System.out.println("Search hits: " + searchHits.getTotalHits());
        return searchHits.stream()
                .map(hit -> hit.getContent().getKeyword())
                .collect(Collectors.toList());
    }

    // Filter
    public List<Film> exploreFilm(){

        NativeQuery criteriaQuery = new NativeQueryBuilder()
                .withQuery(QueryBuilders.bool(b -> b
                        .must(QueryBuilders.term(t -> t.field("keyword").value("a")))
                        .must(QueryBuilders.term(r -> r.field("genre").value("action")))
                        .must(QueryBuilders.term(r -> r.field("season").value("Winter")))
                        .must(QueryBuilders.term(r -> r.field("year").value("2024")))
                ))
                .withPageable(PageRequest.of(0, 10))
                .build();

        SearchHits<Film> searchHits = elasticsearchOperations.search(criteriaQuery, Film.class);
        return searchHits.stream()
                .map(SearchHit::getContent)
                .collect(Collectors.toList());
    }
}