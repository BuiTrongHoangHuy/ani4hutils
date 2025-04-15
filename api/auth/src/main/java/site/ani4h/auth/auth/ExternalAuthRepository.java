package site.ani4h.auth.auth;

import site.ani4h.auth.auth.entity.Auth;
import site.ani4h.auth.auth.entity.AuthRegister;
import site.ani4h.auth.auth.entity.ExternalAuth;
import site.ani4h.auth.auth.entity.ExternalAuthRegister;

public interface ExternalAuthRepository {
    void create(ExternalAuthRegister data);
    ExternalAuth find(int provideId, String externalId);
}
