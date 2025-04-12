package site.ani4h.film.genre;

import lombok.Getter;
import lombok.Setter;
import site.ani4h.shared.common.Image;
import site.ani4h.shared.common.Uid;

import java.time.LocalDateTime;



@Getter
@Setter
public class Genre
{
    private Uid id;
    private String name;
    private String description;
    private Image image;
    private int status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    public final static int type  = 2;
    public void setId(int id) {
        this.id = new Uid(id,0,type);
    }

}
