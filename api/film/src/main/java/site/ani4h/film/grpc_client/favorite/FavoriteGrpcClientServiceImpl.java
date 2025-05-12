package site.ani4h.film.grpc_client.favorite;

import io.grpc.ManagedChannel;
import io.grpc.netty.NettyChannelBuilder;
import org.springframework.stereotype.Service;
import site.ani4h.shared.gen.FavoriteGrpc;
import site.ani4h.shared.gen.FavoriteOuterClass;
import site.ani4h.shared.utils.serviceDiscovery.ServiceDiscovery;
import site.ani4h.shared.utils.serviceDiscovery.ServiceInstance;

import javax.annotation.PreDestroy;
import java.util.List;

@Service
public class FavoriteGrpcClientServiceImpl implements FavoriteGrpcClientService {
    private final ServiceDiscovery serviceDiscovery;
    private final String serviceName = "film";
    private FavoriteGrpc.FavoriteBlockingStub favoriteStub;
    private ManagedChannel channel;

    public FavoriteGrpcClientServiceImpl(ServiceDiscovery serviceDiscovery) {
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
        this.favoriteStub = FavoriteGrpc.newBlockingStub(channel);
    }

    @Override
    public List<Integer> getFilmIdRecentFavorites(int userId, int limit){
        if(favoriteStub == null) {
            initializeChannelAndStub();
        }

        FavoriteOuterClass.GetListFilmIdRecentFavoriteReq request = FavoriteOuterClass.GetListFilmIdRecentFavoriteReq.newBuilder()
                .setUserId(userId)
                .setLimit(limit)
                .build();

        FavoriteOuterClass.ListFilmIdRecentFavoriteRes result = favoriteStub.getListFilmIdRecentFavorite(request);
        List<Integer> filmIds = result.getFilmIdList();

        return filmIds;
    }

    @PreDestroy
    public void shutdown() {
        if (channel != null && !channel.isShutdown()) {
            channel.shutdown();
        }
    }
}
