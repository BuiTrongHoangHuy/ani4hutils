package site.ani4h.auth.auth.entity;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class UserData {
    private LocalDate dateOfBirth;
    private String firstName;
    private String lastName;

    public UserData(LocalDate dateOfBirth, String firstName, String lastName) {
        this.dateOfBirth = dateOfBirth;
        this.firstName = firstName;
        this.lastName = lastName;
    }
}
