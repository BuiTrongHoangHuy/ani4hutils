package site.ani4h.film.grpc_client.history;

import io.grpc.ManagedChannel;
import io.grpc.netty.NettyChannelBuilder;
import org.springframework.stereotype.Service;
import site.ani4h.shared.gen.HistoryGrpc;
import site.ani4h.shared.gen.HistoryOuterClass;
import site.ani4h.shared.utils.serviceDiscovery.ServiceDiscovery;
import site.ani4h.shared.utils.serviceDiscovery.ServiceInstance;

import javax.annotation.PreDestroy;
import java.util.List;

@Service
public class HistoryGrpcClientServiceImpl implements HistoryGrpcClientService {
    private final ServiceDiscovery serviceDiscovery;
    private final String serviceName = "film";
    private HistoryGrpc.HistoryBlockingStub historyStub;
    private ManagedChannel channel;

    public HistoryGrpcClientServiceImpl(ServiceDiscovery serviceDiscovery) {
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
        this.historyStub = HistoryGrpc.newBlockingStub(channel);
    }

    @Override
    public List<Integer> getListFilmIdRecentHistory(int userId, int limit) {
        if(historyStub == null) {
            initializeChannelAndStub();
        }

        HistoryOuterClass.GetListFilmIdRecentHistoryReq request = HistoryOuterClass.GetListFilmIdRecentHistoryReq.newBuilder()
                .setUserId(userId)
                .setLimit(limit)
                .build();

        HistoryOuterClass.ListFilmIdRecentHistoryRes result = historyStub.getListFilmIdRecentHistory(request);
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
