package site.ani4h.shared.common;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Slf4j
@JsonSerialize
@JsonDeserialize
public class Images implements Serializable {
    private List<Image> imageList;

    public Images() {
        this.imageList = new ArrayList<>();
    }
    public Images(List<Image> imageList) {
        this.imageList = imageList != null ? imageList : new ArrayList<>();
    }

    @JsonValue
    public List<Image> getImageList() {
        return imageList;
    }
    @JsonCreator
    public Images(String json) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            if (json != null && !json.isEmpty()) {
                this.imageList = mapper.readValue(json, new TypeReference<List<Image>>() {});
            } else {
                this.imageList = new ArrayList<>();
            }
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to parse images JSON", e);
        }
    }

    public void addImage(Image image) {
        if (image != null) {
            this.imageList.add(image);
        }
    }

    public boolean isEmpty() {
        return imageList.isEmpty();
    }

    public int size() {
        return imageList.size();
    }
}