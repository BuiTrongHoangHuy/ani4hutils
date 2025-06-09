package site.ani4h.auth.subscription;

import org.springframework.stereotype.Service;
import site.ani4h.auth.subscription.entity.Subscription;
import site.ani4h.auth.subscription.entity.SubscriptionRequest;
import site.ani4h.shared.common.Uid;

import java.util.List;

public interface SubscriptionService {
    void createSubscription(SubscriptionRequest request);
    Subscription getSubscriptionById(Uid id);
    void updateSubscription(SubscriptionRequest request);
    void deleteSubscription(Uid id);
    List<Subscription> getSubscriptions();
    List<Subscription> getUserSubscription(int id);
}
