package site.ani4h.film.favorite.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import site.ani4h.shared.common.Image;
import site.ani4h.shared.common.Uid;

import java.util.List;

@Getter
@Setter
public class FavoriteFilm {
    private Uid id;
    private String title;
    private String synopsis;
    private String synonyms;
    private String jaName;
    private String enName;
    private List<Image> images;
    private List<Genre> genres;
    private float avgStar;
    private int totalStar;
    private int maxEpisodes;
    private int numEpisodes;
    private int year;
    private String season;
    private String state;

    @JsonIgnore
    public static final int type = 3;

    public void setId(int id) {
        this.id = new Uid(id,0,type);
    }
}
