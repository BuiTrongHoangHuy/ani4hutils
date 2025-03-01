package site.ani4h.api.auth;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
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
    private Integer id = null;
    @Email
    private String email;

    // Minimum eight characters, at least one letter and one number
    @Pattern(regexp ="^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$")
    private String password;
    private String salt;

}
