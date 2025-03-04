package site.ani4h.api.auth;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginResponse {
    private String email;
    private String accessToken;
    private String refreshToken;
}
