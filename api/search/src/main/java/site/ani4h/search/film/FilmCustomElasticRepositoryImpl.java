package site.ani4h.search.film;

import co.elastic.clients.elasticsearch._types.SortOptionsBuilders;
import co.elastic.clients.elasticsearch._types.SortOrder;
import co.elastic.clients.elasticsearch._types.query_dsl.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.client.elc.NativeQueryBuilder;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.FetchSourceFilterBuilder;
import org.springframework.stereotype.Repository;
import site.ani4h.search.film.entity.*;
import site.ani4h.shared.common.PagingSearch;
import site.ani4h.shared.common.Uid;

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
        NativeQueryBuilder queryBuilder = buildPagingForQuery(paging);
        queryBuilder.withQuery(buildSearchQuery(request));

        NativeQuery searchQuery = queryBuilder.build();

        log.info("Search Query: {}", searchQuery.getQuery());
        SearchHits<Film> searchHits = elasticsearchOperations.search(searchQuery, Film.class);
        log.info("Search Hits: {}", searchHits.getTotalHits());

        List<FilmResponse> data = searchHits.stream()
                .map(searchHit -> {
                    return searchHit.getContent().mapToResponse();
                })
                .collect(Collectors.toList());

        // Set PagingSearch
        PagingSearch pagingSearch = getPagingSearch(paging, searchHits);

        return new SearchResponse(data, pagingSearch);
    }

    @Override
    public SearchResponse moreLikeThisQuery(List<Integer> filmIds, int seed, PagingSearch paging) {
        if(filmIds.isEmpty()){
            return new SearchResponse(List.of(), paging);
        }

        List<String> likedIds = filmIds.stream()
                .map(id -> String.valueOf(id))
                .collect(Collectors.toList());

        List<Like> likes = likedIds.stream()
                .map(id -> Like.of(l -> l
                        .document(doc -> doc
                                .index("films")
                                .id(id)
                        )
                ))
                .collect(Collectors.toList());

        MoreLikeThisQuery mltQuery = MoreLikeThisQuery.of(q -> q
                .fields( "title","genres.name")
                .like(likes)
                .minTermFreq(1)
                .minDocFreq(1)
        );

        Query boolQuery = Query.of(q -> q
                .functionScore(fs -> fs
                        .functions(f -> f
                                .randomScore(r -> r
                                        .seed(String.valueOf(seed))
                                        .field("id")
                                )
                        )
                        .query(q1 -> q1
                                .bool(b -> b
                                        .must(m -> m.moreLikeThis(mltQuery))
                                        .mustNot(mn -> mn.ids(i -> i.values(likedIds)))
                                )
                        )
                )
        );

        NativeQueryBuilder queryBuilder = buildPagingForQuery(paging);
        queryBuilder.withQuery(boolQuery);

        NativeQuery recommend = queryBuilder.build();
        SearchHits<Film> searchHits = elasticsearchOperations.search(recommend, Film.class);
        log.info("Search Hits: {}", searchHits.getTotalHits());

        List<FilmResponse> data = searchHits.stream()
                .map(searchHit -> {
                    return searchHit.getContent().mapToResponse();
                })
                .collect(Collectors.toList());

        PagingSearch pagingSearch = getPagingSearch(paging, searchHits);

        return new SearchResponse(data, pagingSearch);
    }

    @Override
    public List<Integer> randomFilmIds(int size) {
        Query functionScoreQuery = Query.of(q -> q
                .functionScore(fs -> fs
                        .functions(fb -> fb
                                .randomScore(r -> r
                                        .seed(String.valueOf(System.currentTimeMillis()))
                                        .field("id")
                                )
                        )
                        .query(q1 -> q1.matchAll(m -> m))
                )
        );

        NativeQuery query = new NativeQueryBuilder()
                .withQuery(functionScoreQuery)
                .withPageable(PageRequest.of(0, size))
                .withSourceFilter(new FetchSourceFilterBuilder()
                        .withIncludes("id")
                        .build())
                .build();

        SearchHits<Film> searchHits = elasticsearchOperations.search(query, Film.class);

        List<Integer> filmIds = searchHits.stream()
                .map(searchHit -> {
                    return searchHit.getContent().getId();
                })
                .collect(Collectors.toList());

        return filmIds;
    }

    private NativeQueryBuilder buildPagingForQuery(PagingSearch paging) {
        NativeQueryBuilder queryBuilder = new NativeQueryBuilder()
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

        return queryBuilder;
    }

    private static Query buildSearchQuery(SearchRequest request) {
        BoolQuery.Builder boolQuery = QueryBuilders.bool();

        if(request.getTitle() != null && !request.getTitle().isEmpty()){
            boolQuery.must(QueryBuilders.multiMatch(m -> m
                    .fields(
                            "title", "title.standard^2",
                            "synonyms", "synonyms.standard^2",
                            "jaName", "jaName.standard"
                    )
                    .query(request.getTitle())
                    .fuzziness("AUTO")
            ));
        }

        if(request.getGenreId() != null && !request.getGenreId().isEmpty()){
            Uid uid = new Uid(request.getGenreId());
            int id = uid.getLocalId();
            boolQuery.filter(f -> f
                    .term(t -> t.field("genres.id").value(id))
            );
        }

        if(request.getYear() > 1900 && request.getYear() < 2100){
            boolQuery.filter(f -> f
                    .term(t -> t.field("year").value(request.getYear()))
            );
        }

        if(request.getSeason() != null && !request.getSeason().isEmpty()){
            boolQuery.filter(f -> f
                    .term(t -> t.field("season").value(request.getSeason()))
            );
        }

        if(request.getState() != null && !request.getState().isEmpty()){
            boolQuery.filter(f -> f
                    .term(t -> t.field("state").value(request.getState()))
            );
        }

        return Query.of(q -> q
                .bool(boolQuery.build())
        );
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
