package site.ani4h.film.film.entity;

import lombok.Getter;
import lombok.Setter;
import site.ani4h.shared.common.Image;
import site.ani4h.shared.common.Images;
import site.ani4h.shared.common.Uid;

@Getter
@Setter
public class Actor {
    private Uid id;
    private String name;
    private String language;
    private Image image;
    private int status;
    public final static int type = 14;
    
    public void setId(int id) {
        this.id = new Uid(id, 0, type);
    }
}