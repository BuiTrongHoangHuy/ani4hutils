package site.ani4h.shared.common;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Requester {
    private int id;
    private String ip;
    private String userAgent;
    private Role role;

    public void setRole(String role) {
        this.role = Role.fromString(role);
    }
}
