package site.ani4h.film.genre;

import lombok.Getter;
import lombok.Setter;
import site.ani4h.share.common.Image;

@Getter
@Setter
public class GenreCreate
{
    int id;
    String name;
    String description;
    Image image;

}