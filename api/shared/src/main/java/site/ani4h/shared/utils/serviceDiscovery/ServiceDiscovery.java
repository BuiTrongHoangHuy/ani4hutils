package site.ani4h.shared.utils.serviceDiscovery;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ServiceDiscovery {
    ServiceInstance getServiceInstance(String service);
    List<ServiceInstance> getAllServiceInstances(String service);
}
