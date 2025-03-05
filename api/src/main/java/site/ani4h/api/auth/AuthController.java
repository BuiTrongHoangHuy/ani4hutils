package site.ani4h.api.auth;


import co.elastic.clients.elasticsearch.indices.RefreshRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import site.ani4h.api.common.ApiResponse;
import site.ani4h.api.common.UserAlreadyExistsException;

@RestController
@RequestMapping("auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest login) {
        var res = authService.Login(login);
        return ResponseEntity.ok(ApiResponse.success(res));
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest register) throws UserAlreadyExistsException {
        var res = authService.Register(register);
        return ResponseEntity.ok(ApiResponse.success(res.getId()));
    }

    @PostMapping("/refresh")
    public ResponseEntity<?> refresh(@Valid @RequestBody RefreshTokenRequest refresh) {
        var res = authService.RefreshToken(refresh.getRefreshToken());
        return ResponseEntity.ok(ApiResponse.success(res));
    }
}
