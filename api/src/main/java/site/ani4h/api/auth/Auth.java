package site.ani4h.api.auth;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;



@Entity
@Getter
@Setter
public class Auth {
    @Id
    @GeneratedValue
    private Integer id;
    private Integer userId;
    private String email;
    private String password;
    private String salt;
    private Integer status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
