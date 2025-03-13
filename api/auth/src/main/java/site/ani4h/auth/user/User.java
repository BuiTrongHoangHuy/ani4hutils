package site.ani4h.auth.user;


import lombok.Getter;
import lombok.Setter;
import site.ani4h.share.common.Image;
import site.ani4h.share.common.Uid;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
public class User {
    public User(){};
    public User(Uid id, String phoneNumber, String firstName, String lastName, String displayName, LocalDate dateOfBirth, Gender gender, Role role, Image avatar, Integer status, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.phoneNumber = phoneNumber;
        this.firstName = firstName;
        this.lastName = lastName;
        this.displayName = displayName;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.role = role;
        this.avatar = avatar;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
    public void setGender(String gender) {
        this.gender = Gender.fromString(gender);
    }
    public void setRole(String role) {
        this.role = Role.fromString(role);
    }
    public void setId(int id) {
        this.id = new Uid(id,0,type);
    }
    private Uid id;
    private String phoneNumber;
    private String firstName;
    private String lastName;
    private String displayName;
    private LocalDate dateOfBirth;
    private Gender gender;
    private Role role;
    private Image avatar;
    private Integer status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    static public int type = 1;
}
