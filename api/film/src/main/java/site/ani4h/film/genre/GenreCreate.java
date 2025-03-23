package site.ani4h.film.genre;

import lombok.Getter;
import lombok.Setter;
import site.ani4h.shared.common.Image;
import site.ani4h.shared.common.Uid;

@Getter
@Setter
public class GenreCreate
{
    public void setId(int id) {
        this.id = new Uid(id,0,Genre.type);
    }
    private Uid id;
    String name;
    String description;
    Image image;

}