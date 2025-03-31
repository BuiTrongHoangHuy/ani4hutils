package site.ani4h.shared.utils.serviceDiscovery;

public enum InstanceHealth {
    UNKNOWN,
    HEALTHY,
    UNHEALTHY;
    public static InstanceHealth fromString(String string) {
        return string == null? InstanceHealth.UNKNOWN: InstanceHealth.valueOf(string.toUpperCase());
    }
}
