package site.ani4h.auth.subscription.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import site.ani4h.shared.common.Uid;

import java.time.LocalDateTime;

@Getter
@Setter
public class Subscription {
    private Uid id;
    private String name;
    private float price;
    private String quality;
    private String resolution;
    private int maxSimultaneousStreams;
    private int status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    @JsonIgnore
    private static int type = 8;

    public void setId(int id) {
        this.id = new Uid(id,0,type);
    }
}
