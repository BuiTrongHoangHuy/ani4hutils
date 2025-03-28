package site.ani4h.shared.utils.serviceDiscovery;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface ServiceDiscovery {
    ServiceInstance getServiceInstance(String service);
    List<ServiceInstance> getAllServiceInstances(String service);
}
