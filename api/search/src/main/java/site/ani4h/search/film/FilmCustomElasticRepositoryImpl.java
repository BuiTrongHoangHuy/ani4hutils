package site.ani4h.search.film;

import co.elastic.clients.elasticsearch._types.SortOptionsBuilders;
import co.elastic.clients.elasticsearch._types.SortOrder;
import co.elastic.clients.elasticsearch._types.query_dsl.QueryBuilders;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.client.elc.NativeQueryBuilder;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.stereotype.Repository;
import site.ani4h.search.film.entity.*;
import site.ani4h.shared.common.PagingSearch;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Repository
public class FilmCustomElasticRepositoryImpl implements FilmCustomElasticRepository{
    private final ElasticsearchOperations elasticsearchOperations;

    public FilmCustomElasticRepositoryImpl(ElasticsearchOperations elasticsearchOperations) {
        this.elasticsearchOperations = elasticsearchOperations;
    }

    @Override
    public SearchResponse search(SearchRequest request, PagingSearch paging) {

        NativeQueryBuilder queryBuilder = new NativeQueryBuilder()
                .withQuery(QueryBuilders.multiMatch(m -> m
                        .fields(
                                "title", "title.standard^2",
                                "synonyms", "synonyms.standard^2",
                                "jaName", "jaName.standard"
                        )
                        .query(request.getTitle())
                        .fuzziness("AUTO")
                ))
                .withSort(SortOptionsBuilders.score(s -> s.order(SortOrder.Desc)))
                .withSort(SortOptionsBuilders.field(f -> f.field("idSort").order(SortOrder.Asc)));

        if(paging != null) {
            if(paging.getCursor() != null && !paging.getCursor().isEmpty()) {
                int id = getIdFromCursor(paging.getCursor());
                float score = getScoreFromCursor(paging.getCursor());

                queryBuilder.withSearchAfter(List.of(score, id))
                            .withPageable(PageRequest.of(0, 10));
            }
            else {
                queryBuilder.withPageable(PageRequest.of(paging.getPage() - 1, paging.getPageSize()));
            }
        }

        NativeQuery searchQuery = queryBuilder.build();

        log.info("Search Query: {}", searchQuery.getQuery());
        SearchHits<Film> searchHits = elasticsearchOperations.search(searchQuery, Film.class);
        log.info("Search Hits: {}", searchHits.getTotalHits());

        List<Film> data = searchHits.stream()
                .map(searchHit -> {
                    return searchHit.getContent();
                })
                .collect(Collectors.toList());

        // Set PagingSearch
        PagingSearch pagingSearch = getPagingSearch(paging, searchHits);

        return new SearchResponse(data, pagingSearch);
    }

    private static PagingSearch getPagingSearch(PagingSearch paging, SearchHits<Film> searchHits) {
        if(!searchHits.getSearchHits().isEmpty()){
            List<Object> sortValues = searchHits.getSearchHits().get(searchHits.getSearchHits().size() - 1).getSortValues();
            paging.setNextCursor(
                    Objects.requireNonNull(sortValues.get(0)) + "-" + Objects.requireNonNull(sortValues.get(1))
            );

            if(searchHits.getSearchHits().size() < paging.getPageSize()) {
                paging.setNextCursor(null);
            }
        }
        return paging;
    }

    private static int getIdFromCursor(String cursor) {
        String[] parts = cursor.split("-");
        if (parts.length != 2) {
            throw new IllegalArgumentException("Invalid cursor format");
        }
        return Integer.parseInt(parts[1]);
    }

    private static float getScoreFromCursor(String cursor) {
        String[] parts = cursor.split("-");
        if (parts.length != 2) {
            throw new IllegalArgumentException("Invalid cursor format");
        }
        return Float.parseFloat(parts[0]);
    }
}
