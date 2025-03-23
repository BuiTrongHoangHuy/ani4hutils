package site.ani4h.auth.auth;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;
import site.ani4h.shared.common.Uid;

@Getter
@Setter
public class AuthRegister {
    public AuthRegister( Integer userId,String email, String password, String salt) {
        this.userId =  userId;
        this.email = email;
        this.password = password;
        this.salt = salt;
    }
    private Uid id = null;
    public void setId( int id ) {
        this.id = new Uid(id,0,Auth.type);
    }
    @NotBlank
    @NotNull
    private Integer userId;
    @Email
    private String email;

    // Minimum eight characters, at least one letter and one number
    @Pattern(regexp ="^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$")
    private String password;
    private String salt;
}
