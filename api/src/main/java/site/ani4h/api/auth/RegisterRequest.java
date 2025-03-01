package site.ani4h.api.auth;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import site.ani4h.api.user.Role;

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
