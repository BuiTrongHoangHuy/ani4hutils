package site.ani4h.auth.subscription;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import site.ani4h.auth.subscription.entity.Subscription;
import site.ani4h.auth.subscription.entity.SubscriptionRequest;
import site.ani4h.shared.common.Uid;

import java.util.List;

@Slf4j
@Service
public class SubscriptionServiceImpl implements SubscriptionService {
    private final SubscriptionRepository repository;

    public SubscriptionServiceImpl(SubscriptionRepository repository) {
        this.repository = repository;
    }

    @Override
    public void createSubscription(SubscriptionRequest request) {
        log.info("Create subscription {}", request);
        repository.createSubscription(request);
    }

    @Override
    public Subscription getSubscriptionById(Uid id) {
        return repository.getSubscriptionById(id.getLocalId());
    }

    @Override
    public void updateSubscription(SubscriptionRequest request) {
        log.info("Update subscription {}", request);
        repository.updateSubscription(request);
    }

    @Override
    public void deleteSubscription(Uid id) {
        repository.deleteSubscription(id.getLocalId());
    }

    @Override
    public List<Subscription> getSubscriptions() {
        return repository.getSubscriptions();
    }

    @Override
    public Subscription getUserSubscription(int id) {
        return repository.getUserSubscription(id);
    }
}
