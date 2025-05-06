package site.ani4h.search.grpc_client;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import site.ani4h.search.grpc_client.favorite.FavoriteGrpcClientService;
import site.ani4h.shared.common.Uid;

@RestController
@RequestMapping("/v1/auth-grpc")
public class AuthGrpcClientController {
    private final AuthGrpcClientService authGrpcClientService;
    private final FavoriteGrpcClientService favoriteGrpcClientService;

    public AuthGrpcClientController(AuthGrpcClientService authGrpcClientService,
                                     FavoriteGrpcClientService favoriteGrpcClientService)  {
        this.authGrpcClientService = authGrpcClientService;
        this.favoriteGrpcClientService = favoriteGrpcClientService;
    }

    @GetMapping("/user")
    public ResponseEntity<?> getUserById(@RequestParam String id) {
        Uid uid = new Uid(id);
        var user = authGrpcClientService.getUserById(uid.getLocalId());
        return ResponseEntity.ok(user);
    }

    @GetMapping("/favorite")
    public ResponseEntity<?> getUserById(@RequestParam Uid userId,@RequestParam int limit ) {
        var user = favoriteGrpcClientService.getFilmIdRecentFavorites(userId.getLocalId(), limit);
        return ResponseEntity.ok(user);
    }
}
