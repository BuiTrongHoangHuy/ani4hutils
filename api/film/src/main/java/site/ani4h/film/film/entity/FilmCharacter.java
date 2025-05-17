package site.ani4h.film.film.entity;

import lombok.Getter;
import lombok.Setter;
import site.ani4h.shared.common.Image;
import site.ani4h.shared.common.Images;
import site.ani4h.shared.common.Uid;

import java.util.List;

@Getter
@Setter
public class FilmCharacter {
    private Uid id;
    private String name;
    private String role;
    private Image image;
    private int status;
    private List<Actor> actors;
    public final static int type = 15;
    
    public void setId(int id) {
        this.id = new Uid(id, 0, type);
    }
}