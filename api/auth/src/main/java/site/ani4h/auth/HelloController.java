package site.ani4h.auth;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
    @GetMapping("/v1")
    public String index() {
        return "Greetings from Spring Boot!";
    }
}
