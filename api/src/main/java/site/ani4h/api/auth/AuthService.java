package site.ani4h.api.auth;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.ani4h.api.common.UserAlreadyExistsException;
import site.ani4h.api.middleware.jwt_spring_security.JwtUtils;
import site.ani4h.api.middleware.jwt_spring_security.SHA256PasswordEncoder;
import site.ani4h.api.middleware.jwt_spring_security.UserDetailsImpl;
import site.ani4h.api.user.Role;
import site.ani4h.api.user.UserCreate;
import site.ani4h.api.user.UserRepository;
import site.ani4h.api.utils.RandomSequenceGenerator;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Set;

@Service
@Transactional
public class AuthService {
    private final AuthRepository authRepository;
    private final UserRepository userRepository;
    @Autowired
    private Validator validator;
    public AuthService(AuthRepository authRepository, UserRepository userRepository) {
        this.authRepository = authRepository;
        this.userRepository = userRepository;
    }

    @Autowired
    private SHA256PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtils jwtUtils;

    public AuthRegister Register( RegisterRequest req) throws UserAlreadyExistsException {
        Set<ConstraintViolation<RegisterRequest>> violations = validator.validate(req);

        if (!violations.isEmpty()) {
            StringBuilder sb = new StringBuilder();
            for (ConstraintViolation<RegisterRequest> constraintViolation : violations) {
                sb.append(constraintViolation.getMessage());
            }
            throw new ConstraintViolationException("Error occurred: " + sb.toString(), violations);
        }

        // check exists email
        if(this.authRepository.findByEmail(req.getEmail()) != null) {
            throw new UserAlreadyExistsException("User already exists");
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
            throw new RuntimeException("User not found");
        }

        String saltedPassword = auth.getSalt() + req.getPassword();
        if(!passwordEncoder.matches(saltedPassword, auth.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        String jwtAccessToken = jwtUtils.generateAccessToken(req.getEmail());
        String jwtRefreshToken = jwtUtils.generateRefreshToken(req.getEmail());

        LoginResponse res = LoginResponse.builder()
                .email(req.getEmail())
                .accessToken(jwtAccessToken)
                .refreshToken(jwtRefreshToken)
                .build();

        return res;
    }
}
