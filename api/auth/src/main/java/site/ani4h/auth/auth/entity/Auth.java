package site.ani4h.auth.auth.entity;



import lombok.Getter;
import lombok.Setter;
import site.ani4h.shared.common.Role;
import site.ani4h.shared.common.Uid;

import java.time.LocalDateTime;



@Getter
@Setter
public class Auth {
    private Uid id;
    private Uid userId;
    private String email;
    private String password;
    private String salt;
    private Integer status;
    private Role role;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    public void setRole(String role) {
        this.role = Role.fromString(role);
    }
    public void setId (int id ) {
        this.id = new Uid(id,0,type);
    }
    public void setUserId (int userId ) {
        this.userId = new Uid(userId,0,1);
    }
    public static int type = 0;
}
