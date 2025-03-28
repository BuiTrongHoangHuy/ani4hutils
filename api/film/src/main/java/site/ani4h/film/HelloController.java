package site.ani4h.film;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import site.ani4h.shared.common.ApiResponse;
import site.ani4h.shared.utils.serviceDiscovery.ServiceDiscovery;

@RestController
public class HelloController {
    private final ServiceDiscovery serviceDiscovery;

    public HelloController(ServiceDiscovery serviceDiscovery) {
        this.serviceDiscovery = serviceDiscovery;
    }

    @GetMapping("/")
    public ResponseEntity<?> index() {
        if (serviceDiscovery == null) {
            return ResponseEntity.ok("No service discovery");
        }
        return ResponseEntity.ok(ApiResponse.success(
                serviceDiscovery.getServiceInstance("backend"))
        );
    }
}
