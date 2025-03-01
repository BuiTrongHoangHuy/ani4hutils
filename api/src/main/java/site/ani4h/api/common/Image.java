package site.ani4h.api.common;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Image {
    public Image(String id, String url, Integer width, Integer height, String extension, String cloudName) {
        this.id = id;
        this.url = url;
        this.width = width;
        this.height = height;
        this.extension = extension;
        this.cloudName = cloudName;
    }
    private String id;
    private String url;
    private Integer width;
    private Integer height;
    private String extension;
    private String cloudName;
}
