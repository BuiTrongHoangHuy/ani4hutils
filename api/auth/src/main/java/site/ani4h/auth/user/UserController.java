package site.ani4h.auth.user;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import site.ani4h.shared.common.ApiResponse;
import site.ani4h.shared.common.Paging;
import site.ani4h.shared.common.Requester;
import site.ani4h.shared.common.Uid;

@RestController
@RequestMapping("/v1/user")
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
    public ResponseEntity<?> getUsers(HttpServletRequest request,
                                      @ModelAttribute() Paging paging
    ) {
        var requester = Requester.getRequester(request);
        return ResponseEntity.ok(ApiResponse.success(userService.getUsers(paging)));
    }

    @GetMapping("/get-by-email")
    public ResponseEntity<?> getUserIdByEmail(@RequestParam String email) {
        var userId = userService.getUserIdByEmail(email);
        return ResponseEntity.ok(ApiResponse.success(userId));
    }
}
