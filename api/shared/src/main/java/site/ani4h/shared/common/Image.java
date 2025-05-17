package site.ani4h.shared.common;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import hera.util.Base58Utils;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;
import java.util.Map;

@Getter
@Setter
@Slf4j
@JsonSerialize
@JsonDeserialize
public class Image implements Serializable {
    public Image(){}
    @JsonCreator
    public Image(
            @JsonProperty("id") String id,
            @JsonProperty("url") String url,
            @JsonProperty("width") Integer width,
            @JsonProperty("height") Integer height,
            @JsonProperty("extension") String extension,
            @JsonProperty("cloud_name") String cloudName) {
        this.id = id;
        this.url = url;
        this.width = width;
        this.height = height;
        this.extension = extension;
        this.cloudName = cloudName;
    }

    @JsonCreator
    public Image(String str) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            Image image = mapper.readValue(str, Image.class);
            this.id = image.getId();
            this.url = image.getUrl();
            this.width = image.getWidth();
            this.height = image.getHeight();
            this.extension = image.getExtension();
            this.cloudName = image.getCloudName();
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to parse JSON string", e);
        }
    }
    private String id;
    private String url;
    private Integer width;
    private Integer height;
    private String extension;
    private String cloudName;
}
