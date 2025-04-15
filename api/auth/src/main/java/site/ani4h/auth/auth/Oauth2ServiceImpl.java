package site.ani4h.auth.auth;

import jakarta.validation.Validator;
import org.springframework.stereotype.Component;
import site.ani4h.auth.auth.entity.ExternalLoginRequest;
import site.ani4h.auth.auth.entity.LoginResponse;
import site.ani4h.auth.externalprovider.ExternalAuthProvider;
import site.ani4h.auth.externalprovider.ExternalProviderRepository;
import site.ani4h.auth.middleware.jwt_spring_security.JwtUtils;
import site.ani4h.auth.user.UserRepository;
import site.ani4h.shared.errors.EntityNotFound;

@Component
public class Oauth2ServiceImpl implements Oauth2Service {
    private final UserRepository userRepository;
    private final Validator validator;
    private final ExternalProviderRepository externalProviderRepository;
    private final ExternalAuthRepository externalAuthRepository;
    private final ExternalDataRepository externalDataAdapter;
    private final JwtUtils jwtUtils;

    public Oauth2ServiceImpl(UserRepository userRepository, Validator validator, ExternalProviderRepository externalProviderRepository, ExternalAuthRepository externalAuthRepository, ExternalDataRepository externalDataAdapter, JwtUtils jwtUtils) {
        this.userRepository = userRepository;
        this.validator = validator;
        this.externalProviderRepository = externalProviderRepository;
        this.externalAuthRepository = externalAuthRepository;
        this.externalDataAdapter = externalDataAdapter;
        this.jwtUtils = jwtUtils;
    }


    @Override
    public LoginResponse login(ExternalLoginRequest req) {
        var provider = externalProviderRepository.get(req.getProvider().getLocalId());
        var user = externalDataAdapter.getUserData(req, provider);
        var auth = externalAuthRepository.find(provider.getId().getLocalId(),user.getId());
        if (auth == null) {
            throw  new EntityNotFound("user");
        }


        return LoginResponse.builder()
                .build();
    }

    @Override
    public LoginResponse register(ExternalLoginRequest req) {
        var provider = externalProviderRepository.get(req.getProvider().getLocalId());
        var user = externalDataAdapter.getUserData(req, provider);
        return LoginResponse.builder()
                .build();
    }
}
