package site.ani4h.api.auth;

import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final AuthRepository authRepository;
    public AuthService(AuthRepository authRepository) {
        this.authRepository = authRepository;
    }
    public void Register(AuthRegister authRegister) {
        this.authRepository.create(authRegister);
    }
}
