package site.ani4h.auth.auth;


import org.springframework.stereotype.Service;
import site.ani4h.auth.auth.entity.ExternalLoginRequest;
import site.ani4h.auth.auth.entity.LoginResponse;

@Service
public interface Oauth2Service {
    LoginResponse login(ExternalLoginRequest req);
    LoginResponse register(ExternalLoginRequest req);
}
