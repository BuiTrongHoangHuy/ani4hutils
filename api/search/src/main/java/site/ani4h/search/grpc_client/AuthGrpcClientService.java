package site.ani4h.search.grpc_client;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.netty.NettyChannelBuilder;
import net.devh.boot.grpc.client.channelfactory.GrpcChannelFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import site.ani4h.shared.gen.UserGrpc;
import site.ani4h.shared.gen.UserOuterClass;
import site.ani4h.shared.utils.serviceDiscovery.ServiceDiscovery;
import site.ani4h.shared.utils.serviceDiscovery.ServiceInstance;

import javax.annotation.PreDestroy;

@Service
public class AuthGrpcClientService {
    private final ServiceDiscovery serviceDiscovery;
    private final String serviceName = "auth";
    private UserGrpc.UserBlockingStub userStub;
    private ManagedChannel channel;

    public AuthGrpcClientService(ServiceDiscovery serviceDiscovery) {
        this.serviceDiscovery = serviceDiscovery;

        initializeChannelAndStub();
    }

    private void initializeChannelAndStub() {
        ServiceInstance serviceInstance = serviceDiscovery.getServiceInstance(serviceName);
        if (serviceInstance == null) {
            throw new RuntimeException("Service instance not found for service: " + serviceName);
        }

        String address = serviceInstance.getIpv4Address();
        int port = Integer.parseInt(serviceInstance.getPort());
        this.channel = NettyChannelBuilder.forAddress(address,port)
                .usePlaintext()
                .build();
        this.userStub = UserGrpc.newBlockingStub(channel);
    }

    public UserGrpcResponse getUserById(int userId) {
        if(userStub == null) {
            initializeChannelAndStub();
        }
        UserOuterClass.GetUsersByIdReq request = UserOuterClass.GetUsersByIdReq.newBuilder()
                .setId(userId)
                .build();
        UserOuterClass.PublicUserInfo result = userStub.getUserById(request);
        UserGrpcResponse res = new UserGrpcResponse(
                result.getId(),
                result.getFirstName(),
                result.getLastName(),
                result.getUserName(),
                result.getSystemRole()
        );
        return res;
    }

    @PreDestroy
    public void shutdown() {
        if (channel != null && !channel.isShutdown()) {
            channel.shutdown();
        }
    }
}
