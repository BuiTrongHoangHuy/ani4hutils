package site.ani4h.search.film;
import co.elastic.clients.elasticsearch._types.query_dsl.QueryBuilders;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.client.elc.NativeQueryBuilder;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
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

    public List<FilmModel> getFilms() {
        return filmRepository.getFilms();
    }

    public void syncFilmsToElastic() {
        List<FilmModel> films = filmRepository.getFilms();

        if(films.isEmpty()) {
            return;
        }

        log.info("Syncing " + films.size() + " films...");

        // Convert FilmModel to Film
        List<Film> filmList = films.stream()
                .map(filmModel -> {
                    Film film = new Film();
                    film.mapFromFilmModel(filmModel);
                    return film;
                })
                .collect(Collectors.toList());

        filmElasticsearchRepository.saveAll(filmList);
    }

    // Search for films by title
    public List<Film> getFilmsByTitle(String title) throws Exception {
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
    public List<Film> getFilmsByTitleSuggest(String keyword, int page, int pageSize) {
        String formatKeyword = keyword.toLowerCase();

        NativeQuery searchQuery = new NativeQueryBuilder()
                .withQuery(QueryBuilders.matchPhrasePrefix(m->m.field("keyword").query(formatKeyword).maxExpansions(4)))
                .withPageable(PageRequest.of(page, pageSize))
                .build();

        System.out.println("Query: " + searchQuery.getQuery());
        SearchHits<Film> searchHits = elasticsearchOperations.search(searchQuery, Film.class);
        System.out.println("Search hits: " + searchHits.getTotalHits());
        return searchHits.stream()
                .map(SearchHit::getContent)
                .collect(Collectors.toList());
    }

    // Filter
    public List<Film> getFilmsByFilter(String genre, int page, int pageSize){

        NativeQuery criteriaQuery = new NativeQueryBuilder()
                .withQuery(QueryBuilders.bool(b -> b
                        .must(QueryBuilders.term(r -> r.field("genres").value(genre)))
                ))
                .withPageable(PageRequest.of(page, pageSize))
                .build();

        SearchHits<Film> searchHits = elasticsearchOperations.search(criteriaQuery, Film.class);
        return searchHits.stream()
                .map(SearchHit::getContent)
                .collect(Collectors.toList());
    }
}