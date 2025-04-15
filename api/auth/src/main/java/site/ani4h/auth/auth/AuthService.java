package site.ani4h.auth.auth;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.ani4h.auth.auth.entity.*;
import site.ani4h.auth.auth.error.ErrorInvalidCredentials;
import site.ani4h.auth.externalprovider.ExternalProviderRepository;
import site.ani4h.auth.middleware.jwt_spring_security.JwtUtils;
import site.ani4h.auth.middleware.jwt_spring_security.SHA256PasswordEncoder;
import site.ani4h.auth.user.Role;
import site.ani4h.auth.user.UserCreate;
import site.ani4h.auth.user.UserRepository;
import site.ani4h.shared.errors.EntityAlreadyExist;
import site.ani4h.shared.errors.EntityNotFound;
import site.ani4h.shared.errors.ErrorBadRequest;
import site.ani4h.shared.utils.RandomSequenceGenerator;

import java.util.Set;

@Service
@Transactional
public class AuthService {
    private final AuthRepository authRepository;
    private final UserRepository userRepository;
    private final Validator validator;
    private final JwtUtils jwtUtils;
    private final SHA256PasswordEncoder passwordEncoder;

    public AuthService(AuthRepository authRepository, UserRepository userRepository, Validator validator, JwtUtils jwtUtils, SHA256PasswordEncoder passwordEncoder) {
        this.authRepository = authRepository;
        this.userRepository = userRepository;
        this.validator = validator;
        this.jwtUtils = jwtUtils;
        this.passwordEncoder = passwordEncoder;
    }

    public AuthRegister Register( RegisterRequest req)  {
        Set<ConstraintViolation<RegisterRequest>> violations = validator.validate(req);

        if (!violations.isEmpty()) {
            StringBuilder sb = new StringBuilder();
            for (ConstraintViolation<RegisterRequest> constraintViolation : violations) {
                sb.append(constraintViolation.getMessage());
            }
            throw new ConstraintViolationException("Error occurred: " + sb.toString(), violations);
        }
        if(this.authRepository.findByEmail(req.getEmail()) != null) {
            throw new EntityAlreadyExist("User");
        }
        var salt = RandomSequenceGenerator.RandomString(50);
        var displayName = req.getEmail().split("@")[0];

        UserCreate userCreate = new UserCreate(req.getFirstName(),req.getLastName(), Role.USER,displayName,req.getDateOfBirth());
        this.userRepository.create(userCreate);

        String saltedPassword = salt + req.getPassword();
        String encodedPassword = passwordEncoder.encode(saltedPassword);

        AuthRegister auth = new AuthRegister(userCreate.getId(),req.getEmail(), encodedPassword,salt);
        this.authRepository.create(auth);

        return auth;
    }

    public LoginResponse Login(LoginRequest req) {
        Auth auth = this.authRepository.findByEmail(req.getEmail());
        if(auth == null) {
            throw new EntityNotFound("user");
        }

        String saltedPassword = auth.getSalt() + req.getPassword();
        if(!passwordEncoder.matches(saltedPassword, auth.getPassword())) {
            throw new ErrorInvalidCredentials();
        }

        String jwtAccessToken = jwtUtils.generateAccessToken(auth);
        String jwtRefreshToken = jwtUtils.generateRefreshToken(auth);

        return LoginResponse.builder()
                .email(req.getEmail())
                .accessToken(jwtAccessToken)
                .refreshToken(jwtRefreshToken)
                .build();

    }


    public RefreshTokenResponse RefreshToken(String refreshToken) {
        jwtUtils.validateJwtToken(refreshToken);
        if(!jwtUtils.isRefreshToken(refreshToken)) {
            throw new ErrorBadRequest("Invalid refresh token");
        }

        String email = jwtUtils.getClaim(refreshToken,"email");
        Auth auth = this.authRepository.findByEmail(email);

        String accessToken = jwtUtils.generateAccessToken(auth);

        return RefreshTokenResponse.builder()
                .email(email)
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }
}
