package site.ani4h.search.film;

import lombok.Getter;
import lombok.Setter;
import site.ani4h.shared.common.Image;
import site.ani4h.shared.common.Uid;

import java.util.List;


@Getter
@Setter
public class FilmModel {
    private Uid id;
    private String title;
    private List<Image> images;
    private List<String> genres;
    private final static int type = 7;

    public void setId(int id) { this.id = new Uid(id, 0, type); }
}
