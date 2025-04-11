package site.ani4h.auth.externalprovider;

import lombok.Getter;
import lombok.Setter;
import site.ani4h.shared.common.Uid;

import java.time.LocalDateTime;

@Getter
@Setter
public class ExternalAuthProviderCreate {
    private Uid id;
    private String name;
    private String endPoint;
    public void setId(int id ) {
        this.id = new Uid(id,0,1);
    }
}
