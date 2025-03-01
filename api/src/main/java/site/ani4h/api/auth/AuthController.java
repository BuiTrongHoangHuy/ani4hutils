package site.ani4h.api.auth;


import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public Integer register(@RequestBody RegisterRequest register) {
        var res = authService.Register(register);
        return res.getId();
    }
}
