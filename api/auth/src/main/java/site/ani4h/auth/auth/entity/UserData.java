package site.ani4h.auth.auth.entity;

import lombok.Getter;
import lombok.Setter;
import site.ani4h.shared.common.Image;
import java.time.LocalDate;

@Getter
@Setter
public class UserData {
    private LocalDate dateOfBirth;
    private String firstName;
    private String lastName;
    private Image avatar;
    private String id;
    private String email;

    public UserData(String id,String email ,LocalDate dateOfBirth, String firstName, String lastName,Image avatar) {
        this.id = id;
        this.dateOfBirth = dateOfBirth;
        this.firstName = firstName;
        this.lastName = lastName;
        this.avatar = avatar;
    }
}
