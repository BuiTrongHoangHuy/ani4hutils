package site.ani4h.auth.auth.entity;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RefreshTokenResponse {
    private String email;
    private String accessToken;
    private String refreshToken;
}
