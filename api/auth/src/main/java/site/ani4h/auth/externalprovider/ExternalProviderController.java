package site.ani4h.auth.externalprovider;


import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import site.ani4h.shared.common.ApiResponse;
import site.ani4h.shared.common.Requester;
import site.ani4h.shared.common.Uid;
import site.ani4h.shared.errors.ErrorBadRequest;
import site.ani4h.shared.errors.ErrorUnauthorized;

@RestController
@RequestMapping("/v1/oauth-provider")
public class ExternalProviderController {
    private final ExternalProviderService externalProviderService;

    public ExternalProviderController(ExternalProviderService externalProviderService) {
        this.externalProviderService = externalProviderService;
    }


    @PostMapping("")
    public ResponseEntity<?> create(HttpServletRequest request, @RequestBody ExternalAuthProviderCreate create) {
        var requester = Requester.getRequester(request);
        if (requester == null) {
            throw new ErrorUnauthorized();
        }
        this.externalProviderService.create(requester,create);
        return ResponseEntity.ok(ApiResponse.success(create.getId()));
    }
    @PatchMapping("/{id}")
    public ResponseEntity<?> update(HttpServletRequest request, @RequestBody ExternalAuthProviderUpdate update,
                                    @PathVariable Uid id) {
        var requester = Requester.getRequester(request);
        if (requester == null) {
            throw new ErrorUnauthorized();
        }
        this.externalProviderService.update(requester,id.getLocalId(),update);
        return ResponseEntity.ok(ApiResponse.success(true));
    }

    @GetMapping("")
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok(ApiResponse.success(this.externalProviderService.listAll()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(HttpServletRequest request, @PathVariable Uid id) {
        var requester = Requester.getRequester(request);
        if (requester == null) {
            throw new ErrorUnauthorized();
        }
        this.externalProviderService.delete(requester,id.getLocalId());
        return ResponseEntity.ok(ApiResponse.success(true));
    }
}
