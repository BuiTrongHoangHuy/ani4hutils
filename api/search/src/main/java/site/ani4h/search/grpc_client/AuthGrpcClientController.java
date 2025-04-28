package site.ani4h.search.grpc_client;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import site.ani4h.shared.common.Uid;

@RestController
@RequestMapping("/v1/auth-grpc")
public class AuthGrpcClientController {
    private final AuthGrpcClientService authGrpcClientService;

    public AuthGrpcClientController(AuthGrpcClientService authGrpcClientService) {
        this.authGrpcClientService = authGrpcClientService;
    }

    @GetMapping("/user")
    public ResponseEntity<?> getUserById(@RequestParam String id) {
        Uid uid = new Uid(id);
        var user = authGrpcClientService.getUserById(uid.getLocalId());
        return ResponseEntity.ok(user);
    }
}
