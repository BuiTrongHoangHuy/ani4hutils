package site.ani4h.api.auth;



import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;



@Getter
@Setter
public class Auth {
    private Integer id;
    private Integer userId;
    private String email;
    private String password;
    private String salt;
    private Integer status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
