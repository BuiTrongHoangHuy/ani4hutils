package site.ani4h.search.film.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.Setting;
import site.ani4h.shared.common.Image;

import java.util.List;

@Document(indexName = "films")
@Setting(settingPath = "/elasticsearch/film-settings.json")
@Getter
@Setter
public class Film {
    @Id
    private String id;

    @Field(type = FieldType.Text, analyzer = "autocomplete", searchAnalyzer = "autocomplete_search")
    private String title;

    @Field(type = FieldType.Search_As_You_Type)
    private String keyword;

    @Field(type = FieldType.Keyword)
    private List<String> genres;

    @Field(type = FieldType.Keyword)
    private String synopsis;

    @Field(type = FieldType.Nested)
    private List<Image> images;

    @Field(type = FieldType.Text, analyzer = "autocomplete", searchAnalyzer = "autocomplete_search")
    private String synonyms;

    @Field(type = FieldType.Text, analyzer = "autocomplete", searchAnalyzer = "autocomplete_search")
    private String ja_name;

    @Field(type = FieldType.Text, analyzer = "autocomplete", searchAnalyzer = "autocomplete_search")
    private String en_name;

    public Film() {
    }

    public void mapFromFilmModel(FilmModel filmModel) {
        this.id = filmModel.getId().toString();
        this.title = filmModel.getTitle();
        this.keyword = filmModel.getTitle();
        this.genres = filmModel.getGenres();
        this.images = filmModel.getImages();
        this.synopsis = filmModel.getSynopsis();
        this.synonyms = filmModel.getSynonyms();
        this.ja_name = filmModel.getJa_name();
        this.en_name = filmModel.getEn_name();
    }
}
