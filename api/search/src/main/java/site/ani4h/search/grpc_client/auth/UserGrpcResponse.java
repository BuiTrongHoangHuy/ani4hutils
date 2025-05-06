package site.ani4h.search.grpc_client.auth;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserGrpcResponse {
    private int id;
    private String firstName;
    private String lastName;
    private String username;
    private String systemRole;

    public UserGrpcResponse(int id, String firstName, String lastName, String username, String systemRole) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.systemRole = systemRole;
    }
}
