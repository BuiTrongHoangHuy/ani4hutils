package site.ani4h.api.user;

import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("user")
public class UserController {
    private final UserService userService;


    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("{id}")
    public ResponseEntity<User> getUser(@PathVariable()int id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }
}
