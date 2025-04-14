package site.ani4h.auth.externalprovider;

import lombok.Getter;
import lombok.Setter;
import site.ani4h.shared.common.Uid;

import java.time.LocalDateTime;

@Getter
@Setter
public class ExternalAuthProvider {
    private Uid id;
    private String name;
    private String endPoint;
    private int status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static int type = 6;
    public void setId(int id ) {
        this.id = new Uid(id,0,type);
    }
}
