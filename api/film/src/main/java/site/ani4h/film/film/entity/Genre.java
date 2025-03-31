package site.ani4h.film.film.entity;

import lombok.Getter;
import lombok.Setter;
import site.ani4h.shared.common.Uid;

@Getter
@Setter
public class Genre {
    private Uid id;
    private String name;
    public final static int type  = 2;
    public void setId(int id) {
        this.id = new Uid(id,0,type);
    }
}
