package site.ani4h.api.auth;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthRegister {
    public AuthRegister(String email, String password, String salt) {
        this.email = email;
        this.password = password;
        this.salt = salt;
    }
    private String email;
    private String password;
    private String salt;
}
