package site.ani4h.auth.auth;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RefreshTokenRequest {
    private String refreshToken;
}
