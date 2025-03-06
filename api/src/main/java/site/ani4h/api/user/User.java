package site.ani4h.api.user;


import lombok.Getter;
import lombok.Setter;
import site.ani4h.api.common.Image;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
public class User {
    public User(){};
    public User(int id, String phoneNumber, String firstName, String lastName, String displayName, LocalDate dateOfBirth, Gender gender, Role role, Image avatar, Integer status, LocalDateTime createdAt, LocalDateTime updatedAt) {
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
    private int id;
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
}
