package site.ani4h.api.auth;


import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("auth")
public class AuthController {
    @PostMapping("/login")
    public String login() {
        return "Greetings from Spring Boot!";
    }


    @PostMapping("/register")
    public String register() {
        return "Greetings from Spring Boot!";
    }
}
