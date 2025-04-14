package site.ani4h.search.film;

import co.elastic.clients.elasticsearch._types.SortOptions;
import co.elastic.clients.elasticsearch._types.SortOptionsBuilders;
import co.elastic.clients.elasticsearch._types.SortOrder;
import co.elastic.clients.elasticsearch._types.query_dsl.QueryBuilders;
import co.elastic.clients.elasticsearch._types.query_dsl.TextQueryType;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.client.elc.NativeQueryBuilder;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.stereotype.Repository;
import site.ani4h.search.film.entity.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Repository
public class FilmCustomElasticRepositoryImpl implements FilmCustomElasticRepository{
    private final ElasticsearchOperations elasticsearchOperations;

    public FilmCustomElasticRepositoryImpl(ElasticsearchOperations elasticsearchOperations) {
        this.elasticsearchOperations = elasticsearchOperations;
    }

    @Override
    public SearchResponse search(SearchRequest request) {
        List<SortOptions> sortOptions = new ArrayList<>();

        // Sort by score in descending order
        sortOptions.add(
                SortOptionsBuilders.score(s -> s
                        .order(SortOrder.Desc)
                )
        );

        // Sort by Uid in descending order
        sortOptions.add(
                SortOptionsBuilders.field(f -> f
                        .field("uid")
                        .order(SortOrder.Asc)
                )
        );

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
                .withSort(sortOptions)
                .withPageable(PageRequest.of(0, 10));

        if (request.getUid() != null && request.getScore() != null) {
            if(request.getUid().isEmpty() || request.getScore() == 0) {
                throw new IllegalArgumentException("Uid and score must not be empty or zero");
            }
            else {
                queryBuilder.withSearchAfter(List.of(request.getScore(), request.getUid()));
            }
        }

        NativeQuery searchQuery = queryBuilder.build();

        System.out.println("Query: " + searchQuery.getQuery());
        SearchHits<FilmResponse> searchHits = elasticsearchOperations.search(searchQuery, FilmResponse.class);
        System.out.println("Search hits: " + searchHits.getTotalHits());

        // Duyệt qua các kết quả và gán điểm số vào FilmResponse
        List<FilmResponse> data = searchHits.stream()
                .map(searchHit -> {
                    FilmResponse filmResponse = searchHit.getContent();  // Lấy đối tượng FilmResponse
                    filmResponse.setScore(searchHit.getScore());  // Gán score từ SearchHit vào FilmResponse
                    return filmResponse;
                })
                .collect(Collectors.toList());

        // Set PagingSearch
        PagingSearch pagingSearch = new PagingSearch();
        pagingSearch.setTotal((int) searchHits.getTotalHits());
        if(!searchHits.getSearchHits().isEmpty()){
            List<Object> sortValues = searchHits.getSearchHits().get(searchHits.getSearchHits().size() - 1).getSortValues();
            pagingSearch.setUid(
                    Objects.requireNonNull(sortValues.get(1)).toString()
            );
            pagingSearch.setScore(
                    Float.parseFloat(Objects.requireNonNull(sortValues.get(0)).toString())
            );
        }

        SearchResponse searchResponse = new SearchResponse(data, pagingSearch);

        return searchResponse;
    }
}
