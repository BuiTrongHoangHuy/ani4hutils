package site.ani4h.auth.auth.entity;



import lombok.Getter;
import lombok.Setter;
import site.ani4h.shared.common.Uid;

import java.time.LocalDateTime;



@Getter
@Setter
public class Auth {
    private Uid id;
    private Integer userId;
    private String email;
    private String password;
    private String salt;
    private Integer status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    public void setId (int id ) {
        this.id = new Uid(id,0,type);
    }
    public static int type = 0;
}
