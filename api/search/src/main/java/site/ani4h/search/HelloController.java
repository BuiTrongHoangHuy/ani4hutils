package site.ani4h.search;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import site.ani4h.shared.utils.serviceDiscovery.ServiceDiscovery;
import site.ani4h.shared.utils.serviceDiscovery.ServiceInstance;

@Slf4j
@RestController
@RequestMapping
public class HelloController {
    private final ServiceDiscovery serviceDiscovery;

    public HelloController(ServiceDiscovery serviceDiscovery) {
        this.serviceDiscovery = serviceDiscovery;
    }

    @GetMapping("/discovery")
    public ResponseEntity<?> TestDiscovery(@RequestParam String service) {
        log.info("Test discovery service " + service);
        ServiceInstance serviceInstance = serviceDiscovery.getServiceInstance(service);
        return ResponseEntity.ok(serviceInstance);
    }
}
