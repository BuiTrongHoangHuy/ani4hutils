package site.ani4h.film.genre;

import lombok.Getter;
import lombok.Setter;
import site.ani4h.share.common.Image;

import java.time.LocalDateTime;



@Getter
@Setter
public class Genre
{
    int id;
    String name;
    String description;
    Image image;
    int status;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
}
