package site.ani4h.api.auth;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.ani4h.api.user.Role;
import site.ani4h.api.user.UserCreate;
import site.ani4h.api.user.UserRepository;
import site.ani4h.api.utils.RandomSequenceGenerator;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
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
    public AuthRegister Register( RegisterRequest req) {
        Set<ConstraintViolation<RegisterRequest>> violations = validator.validate(req);

        if (!violations.isEmpty()) {
            StringBuilder sb = new StringBuilder();
            for (ConstraintViolation<RegisterRequest> constraintViolation : violations) {
                sb.append(constraintViolation.getMessage());
            }
            throw new ConstraintViolationException("Error occurred: " + sb.toString(), violations);
        }

        var salt = RandomSequenceGenerator.RandomString(50);
        MessageDigest digest = null;
        try {
            digest = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        var displayName = req.getEmail().split("@")[0];
        UserCreate userCreate = new UserCreate(req.getFirstName(),req.getLastName(), Role.USER,displayName,req.getDateOfBirth());
        this.userRepository.create(userCreate);
        byte[] hash = digest.digest((salt + req.getPassword()).getBytes(StandardCharsets.UTF_8));
        AuthRegister auth = new AuthRegister(userCreate.getId(),req.getEmail(), Arrays.toString(hash),salt);
        this.authRepository.create(auth);
        return auth;
    }
}
