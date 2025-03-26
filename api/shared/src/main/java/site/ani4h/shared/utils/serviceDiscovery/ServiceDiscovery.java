package site.ani4h.shared.utils.serviceDiscovery;

import java.util.List;

public interface ServiceDiscovery {
    ServiceInstance getServiceInstance(String service);
    List<ServiceInstance> getAllServiceInstances(String service);
}
