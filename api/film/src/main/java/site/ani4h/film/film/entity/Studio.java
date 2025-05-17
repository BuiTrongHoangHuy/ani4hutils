package site.ani4h.film.film.entity;

import lombok.Getter;
import lombok.Setter;
import site.ani4h.shared.common.Image;
import site.ani4h.shared.common.Uid;

@Getter
@Setter
public class Studio {
    private Uid id;
    private String name;
    private Image image;
    private String description;
    private int status;
    public final static int type = 18;
    
    public void setId(int id) {
        this.id = new Uid(id, 0, type);
    }
}