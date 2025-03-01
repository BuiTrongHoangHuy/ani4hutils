package site.ani4h.api.auth;


import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }
    @PostMapping("/login")
    public String login() {
        return "Greetings from Spring Boot!";
    }


    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest register) {
        var res = authService.Register(register);
        return ResponseEntity.ok(res.getId());
    }
}
