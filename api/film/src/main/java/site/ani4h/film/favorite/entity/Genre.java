package site.ani4h.film.favorite.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import site.ani4h.shared.common.Uid;

@Getter
@Setter
public class Genre {
    private Uid id;
    private String name;

    @JsonIgnore
    public final static int type  = 2;

    public void setId(int id) {
        this.id = new Uid(id,0,type);
    }
}