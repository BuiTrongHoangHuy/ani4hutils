package site.ani4h.auth.user;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import site.ani4h.share.common.ApiResponse;
import site.ani4h.share.common.Paging;
import site.ani4h.share.common.Uid;

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
    public ResponseEntity<?> getUsers(
            @ModelAttribute() Paging paging
    ) {
        System.out.println(paging.getPage());
        return ResponseEntity.ok(ApiResponse.success(userService.getUsers(paging)));
    }
}
