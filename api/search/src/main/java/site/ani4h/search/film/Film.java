package site.ani4h.search.film;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import site.ani4h.shared.common.Image;

import java.util.List;

@Document(indexName = "films")
@Getter
@Setter
public class Film {
    @Id
    private String id;

    @Field(type = FieldType.Text)
    private String title;

    @Field(type = FieldType.Search_As_You_Type)
    private String keyword;

    @Field(type = FieldType.Keyword)
    private List<String> genres;

    @Field(type = FieldType.Keyword)
    private String synopsis;

    @Field(type = FieldType.Nested)
    private List<Image> images;

    public Film() {
    }

    public void mapFromFilmModel(FilmModel filmModel) {
        this.id = filmModel.getId().toString();
        this.title = filmModel.getTitle();
        this.keyword = filmModel.getTitle();
        this.genres = filmModel.getGenres();
        this.images = filmModel.getImages();
        this.synopsis = filmModel.getSynopsis();
    }
}
