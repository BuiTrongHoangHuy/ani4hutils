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

    @Field(type = FieldType.Keyword)
    private List<String> genres;

    @Field(type = FieldType.Keyword)
    private String synopsis;

    @Field(type = FieldType.Nested)
    private List<Image> images;

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

    public Film() {
    }

    public void mapFromFilmModel(FilmModel filmModel) {
        this.id = filmModel.getId().getLocalId();
        this.title = filmModel.getTitle();
        this.idSort = filmModel.getId().getLocalId();
        this.genres = filmModel.getGenres();
        this.images = filmModel.getImages();
        this.synopsis = filmModel.getSynopsis();
        this.synonyms = filmModel.getSynonyms();
        this.jaName = filmModel.getJa_name();
        this.enName = filmModel.getEn_name();
    }
}
