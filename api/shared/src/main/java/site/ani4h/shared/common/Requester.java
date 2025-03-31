package site.ani4h.shared.common;

import jakarta.servlet.http.HttpServletRequest;
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
    public static Requester getRequester(HttpServletRequest request) {
        return (Requester) request.getAttribute("requester");
    }
}
