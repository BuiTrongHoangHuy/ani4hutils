package site.ani4h.shared.utils.serviceDiscovery;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ServiceInstance {
    private String service;
    private String ipv4Address;
    private String ipv6Address;
    private String port;
    public ServiceInstance(String service,
                           String ipv4Address,
                           String ipv6Address,
                           String port,
                           String healthStatus) {
        this.service = service;
        this.ipv4Address = ipv4Address;
        this.ipv6Address = ipv6Address;
        this.port = port;
        this.healthStatus = InstanceHealth.fromString(healthStatus);
    }
    public ServiceInstance() {}
    private InstanceHealth healthStatus;

    public void setHealthStatus(String healthStatus) {
        this.healthStatus = InstanceHealth.fromString(healthStatus);
    }
}
