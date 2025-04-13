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
import site.ani4h.search.film.entity.*;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class FilmService {
    private final FilmRepository filmRepository;
    private final FilmElasticsearchRepository filmElasticsearchRepository;
    private final ElasticsearchOperations elasticsearchOperations;
    private final FilmCustomElasticRepository filmCustomElasticRepository;

    @Autowired
    public FilmService(
            FilmRepository filmRepository,
            FilmElasticsearchRepository filmElRepository,
            ElasticsearchOperations elasticsearchOperations,
            FilmCustomElasticRepository filmCustomElasticRepository
    ) {
        this.filmRepository = filmRepository;
        this.filmElasticsearchRepository = filmElRepository;
        this.elasticsearchOperations = elasticsearchOperations;
        this.filmCustomElasticRepository = filmCustomElasticRepository;
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
    public SearchResponse getFilmsByTitle(SearchRequest request) throws Exception {
        return filmCustomElasticRepository.search(request);
    }
}