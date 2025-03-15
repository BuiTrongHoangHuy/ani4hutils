package site.ani4h.auth.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import site.ani4h.auth.user.Role;

import java.time.LocalDate;

@Getter
@Setter
public class RegisterRequest {
    public RegisterRequest(String email, String password, String firstName, String lastName, Role role, LocalDate birthday) {
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.role = role;
        this.dateOfBirth = birthday;
    }
    @NotNull
    private LocalDate dateOfBirth;
    @Email
    private String email;
    private String password;
    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;
    private Role role;
}
