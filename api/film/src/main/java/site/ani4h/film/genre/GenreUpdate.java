package site.ani4h.film.genre;

import lombok.Getter;
import lombok.Setter;
import site.ani4h.shared.common.Image;

@Getter
@Setter
public class GenreUpdate
{
    String name;
    String description;
    Image image;
}
