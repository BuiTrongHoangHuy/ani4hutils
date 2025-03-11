package site.ani4h.auth.user;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class UserCreate {
    public UserCreate(String firstName, String lastName, Role role, String displayName, LocalDate birthday) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.role = role;
        this.displayName = displayName;
        this.dateOfBirth = birthday;
    }
    private Integer id;
    @NotNull
    private String firstName;
    @NotNull
    private String lastName;
    @NotNull
    private Role role;
    @NotNull
    private LocalDate dateOfBirth;
    @NotNull
    private String displayName;
}
