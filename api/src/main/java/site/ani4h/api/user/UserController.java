package site.ani4h.api.user;

import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import site.ani4h.api.common.ApiResponse;
import site.ani4h.api.common.Uid;

@RestController
@RequestMapping("user")
public class UserController {
    private final UserService userService;


    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("{id}")
    public ResponseEntity<?> getUser(@PathVariable() Uid id) {
        return ResponseEntity.ok(ApiResponse.success(userService.getUserById(id.getLocalId())));
    }

    @GetMapping("")
    public ResponseEntity<?> getUsers() {
        return ResponseEntity.ok(ApiResponse.success(userService.getUsers()));
    }
}
