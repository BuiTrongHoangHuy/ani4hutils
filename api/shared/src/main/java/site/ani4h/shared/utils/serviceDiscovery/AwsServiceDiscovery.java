package site.ani4h.shared.utils.serviceDiscovery;


import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.servicediscovery.ServiceDiscoveryClient;
import software.amazon.awssdk.services.servicediscovery.model.DiscoverInstancesRequest;

import java.util.*;

@Component
public class AwsServiceDiscovery implements ServiceDiscovery {
    private ServiceDiscoveryClient client;

    private final String namespace = "ani4h";
    @Value("${aws.access-key-id}")
    private String accessKey;
    @Value("${aws.access-secret-key}")
    private String accessSecret;
    private final Map<String, List<ServiceInstance>> instances =
            Collections.synchronizedMap(new HashMap<>());

    // Debug
    @Value("${debug}")
    private boolean debug;

    @Value("${auth.http.server.port}")
    private int authHttpPort;
    @Value("${auth.grpc.server.port}")
    private int authGrpcPort;

    @Value("${film.http.server.port}")
    private int filmHttpPort;
    @Value("${film.grpc.server.port}")
    private int filmGrpcPort;

    @Value("${search.http.server.port}")
    private int searchHttpPort;
    @Value("${search.grpc.server.port}")
    private int searchGrpcPort;


    public AwsServiceDiscovery() {}
    @PostConstruct
    public void init() {
        if (accessKey == null || accessSecret == null || namespace == null) {
            throw new IllegalStateException("AWS credentials or namespace not properly configured");
        }
        this.client = ServiceDiscoveryClient.builder()
                .region(Region.AP_SOUTHEAST_1)
                .credentialsProvider(StaticCredentialsProvider.create(
                        AwsBasicCredentials.create(this.accessKey, this.accessSecret)))
                .build();
    }

    private void fetchServiceInstances(String service) {
        var serviceInstances = new ArrayList<ServiceInstance>();
        var request = DiscoverInstancesRequest.builder()
                .namespaceName(namespace)
                .serviceName(service)
                .build();
        var response = this.client.discoverInstances(request);
        if (response.hasInstances()) {
            System.out.println("Found " + response.instances().size() + " instances");
        }
        else {
            System.out.println("No instances found");
        }
        for (var instance : response.instances()) {
            System.out.println(instance.hasAttributes());
            var serviceInstance = new ServiceInstance(
                    service,
                    instance.attributes().get("AWS_INSTANCE_IPV4"),
                    instance.attributes().get("AWS_INSTANCE_IPV6"),
                    instance.attributes().get("AWS_INSTANCE_PORT"),
                    instance.healthStatusAsString()
            );
            serviceInstances.add(serviceInstance);
        }
        this.instances.put(service, serviceInstances);
    }

    @Scheduled(fixedRate = 30000)
    public void refreshAllServices() {
        synchronized (instances) {
            for (String service : instances.keySet()) {
                fetchServiceInstances(service);
            }
        }
    }

    @Override
    public ServiceInstance getServiceInstance(String service) {
        if(debug){
            if(service.equals("auth")){
                String port = String.valueOf(authGrpcPort);
                return new ServiceInstance(service, "localhost", "localhost", port, InstanceHealth.HEALTHY.toString());
            }
            else if(service.equals("film")){
                String port = String.valueOf(filmGrpcPort);
                return new ServiceInstance(service, "localhost", "localhost", port, InstanceHealth.HEALTHY.toString());
            }
            else if(service.equals("search")){
                String port = String.valueOf(searchGrpcPort);
                return new ServiceInstance(service, "localhost", "localhost", port, InstanceHealth.HEALTHY.toString());
            }
            else{
                return null;
            }
        }

        System.out.println("CALLLL");
        if (!this.instances.containsKey(service)) {
            fetchServiceInstances(service);
        }
        List<ServiceInstance> serviceInstances = this.instances.get(service);
        if (serviceInstances == null || serviceInstances.isEmpty()) {
            return null;
        }
        return serviceInstances.stream()
                .filter(instance -> InstanceHealth.HEALTHY == instance.getHealthStatus())
                .findFirst()
                .orElse(serviceInstances.get(0));
    }

    @Override
    public List<ServiceInstance> getAllServiceInstances(String service) {
        if (!this.instances.containsKey(service)) {
            fetchServiceInstances(service);
        }
        return Collections.unmodifiableList(this.instances.get(service)); // Trả về danh sách không thể sửa đổi
    }
}