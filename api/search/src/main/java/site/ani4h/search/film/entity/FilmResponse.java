package site.ani4h.search.film.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.elasticsearch.annotations.Document;
import site.ani4h.shared.common.Image;

import java.util.List;

@Document(indexName = "films")
@Getter
@Setter
public class FilmResponse {
    private String id;
    private String uid;
    private String title;
    private List<Image> images;
    private List<String> genres;
    private String synopsis;
    private String synonyms;
    private String jaName;
    private String enName;

    private float score;
}
