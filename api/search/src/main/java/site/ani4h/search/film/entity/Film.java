package site.ani4h.search.film.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.*;
import site.ani4h.shared.common.Image;

import java.util.List;

@Document(indexName = "films")
@Setting(settingPath = "/elasticsearch/film-settings.json")
@Getter
@Setter
public class Film {
    @Id
    private int id;

    @Field(type = FieldType.Keyword)
    private int idSort;

    @MultiField(
            mainField = @Field(type = FieldType.Text, analyzer = "autocomplete", searchAnalyzer = "autocomplete_search"),
            otherFields = {
                    @InnerField(suffix = "standard", type = FieldType.Text, analyzer = "standard")
            }
    )
    private String title;

    @MultiField(
            mainField = @Field(type = FieldType.Text, analyzer = "autocomplete", searchAnalyzer = "autocomplete_search"),
            otherFields = {
                    @InnerField(suffix = "standard", type = FieldType.Text, analyzer = "standard")
            }
    )
    private String synonyms;

    @MultiField(
            mainField = @Field(type = FieldType.Text, analyzer = "autocomplete", searchAnalyzer = "autocomplete_search"),
            otherFields = {
                    @InnerField(suffix = "standard", type = FieldType.Text, analyzer = "standard")
            }
    )
    private String jaName;

    @MultiField(
            mainField = @Field(type = FieldType.Text, analyzer = "autocomplete", searchAnalyzer = "autocomplete_search"),
            otherFields = {
                    @InnerField(suffix = "standard", type = FieldType.Text, analyzer = "standard")
            }
    )
    private String enName;

    @Field(type = FieldType.Object)
    private List<Genre> genres;

    @Field(type = FieldType.Keyword)
    private String synopsis;

    @Field(type = FieldType.Nested)
    private List<Image> images;

    @Field(type = FieldType.Keyword)
    private float avgStar;

    @Field(type = FieldType.Keyword)
    private int totalStar;

    @Field(type = FieldType.Keyword)
    private int maxEpisodes;

    @Field(type = FieldType.Keyword)
    private int numEpisodes;

    @Field(type = FieldType.Keyword)
    private int year;

    @Field(type = FieldType.Keyword)
    private String season;

    @Field(type = FieldType.Keyword)
    private String state;

    public Film() {
    }

    public FilmResponse mapToResponse() {
        FilmResponse filmResponse = new FilmResponse();
        filmResponse.setId(this.id);
        filmResponse.setTitle(this.title);
        filmResponse.setSynopsis(this.synopsis);
        filmResponse.setSynonyms(this.synonyms);
        filmResponse.setJaName(this.jaName);
        filmResponse.setEnName(this.enName);
        filmResponse.setGenres(this.genres.stream().map(Genre::mapToGenreResponse).toList());
        filmResponse.setImages(this.images);
        filmResponse.setAvgStar(this.avgStar);
        filmResponse.setTotalStar(this.totalStar);
        filmResponse.setMaxEpisodes(this.maxEpisodes);
        filmResponse.setNumEpisodes(this.numEpisodes);
        filmResponse.setYear(this.year);
        filmResponse.setSeason(this.season);
        filmResponse.setState(this.state);

        return filmResponse;
    }
}
