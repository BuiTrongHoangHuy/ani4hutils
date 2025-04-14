package site.ani4h.auth.auth;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import site.ani4h.auth.auth.entity.*;
import site.ani4h.auth.externalprovider.ExternalAuthProvider;
import site.ani4h.auth.externalprovider.ExternalProviderRepository;
import site.ani4h.shared.common.ApiResponse;

@RestController
@RequestMapping("/v1/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;

    }
    @PostMapping("/login")
    public ResponseEntity<?> login(
            HttpServletRequest request,
            @RequestBody LoginRequest login) {
        var res = authService.Login(login);
        return ResponseEntity.ok(ApiResponse.success(res));
    }
    @PostMapping("/oauth")
    public ResponseEntity<?> externalLogin(
            @RequestBody ExternalLoginRequest oauthRequest) {
        var res = authService.ExternalLogin(oauthRequest);
        return ResponseEntity.ok(ApiResponse.success(res));
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody @Valid RegisterRequest register)  {
        var res = authService.Register(register);
        return ResponseEntity.ok(res.getUserId());
    }

    @PostMapping("/refresh")
    public ResponseEntity<?> refresh(@Valid @RequestBody RefreshTokenRequest refresh) {
        var res = authService.RefreshToken(refresh.getRefreshToken());
        return ResponseEntity.ok(ApiResponse.success(res));
    }
}
