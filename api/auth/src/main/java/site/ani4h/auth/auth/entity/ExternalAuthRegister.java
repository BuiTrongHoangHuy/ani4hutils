package site.ani4h.auth.auth.entity;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class ExternalAuthRegister {
    private int userId;
    private String token;
    private String externalUserId;
    private int providerId;
    public ExternalAuthRegister(int userId, String token, String externalUserId, int providerId) {
        this.userId = userId;
        this.token = token;
        this.externalUserId = externalUserId;
        this.providerId = providerId;
    }
}
