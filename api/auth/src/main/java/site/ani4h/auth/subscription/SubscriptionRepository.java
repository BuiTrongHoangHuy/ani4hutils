package site.ani4h.auth.subscription;

import org.springframework.stereotype.Repository;
import site.ani4h.auth.subscription.entity.Subscription;
import site.ani4h.auth.subscription.entity.SubscriptionRequest;

import java.util.List;

public interface SubscriptionRepository {
    void createSubscription(SubscriptionRequest subscription);
    Subscription getSubscriptionById(int id);
    void updateSubscription(SubscriptionRequest subscription);
    void deleteSubscription(int id);
    List<Subscription> getSubscriptions();
}
