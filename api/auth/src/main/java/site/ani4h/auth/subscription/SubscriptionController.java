package site.ani4h.auth.subscription;

import org.springframework.web.bind.annotation.*;
import site.ani4h.auth.subscription.entity.Subscription;
import site.ani4h.auth.subscription.entity.SubscriptionRequest;
import site.ani4h.shared.common.Uid;

import java.util.List;
@RestController
@RequestMapping("/v1")
public class SubscriptionController {
    private final SubscriptionService subscriptionService;

    public SubscriptionController(SubscriptionService subscriptionService) {
        this.subscriptionService = subscriptionService;
    }

    @PostMapping("/subscription/create")
    public void createSubscription(@RequestBody SubscriptionRequest request) {
        subscriptionService.createSubscription(request);
    }

    @PatchMapping("/subscription/update")
    public void updateSubscription(@RequestBody SubscriptionRequest request) {
        subscriptionService.updateSubscription(request);
    }

    @DeleteMapping("/subscription/delete")
    public void deleteSubscription(@RequestParam Uid id) {
        subscriptionService.deleteSubscription(id);
    }

    @GetMapping("/subscription/get-by-id")
    public Subscription getSubscription(@RequestParam Uid id) {
        return subscriptionService.getSubscriptionById(id);
    }

    @GetMapping("/subscription/")
    public List<Subscription> getSubscriptions() {
        return subscriptionService.getSubscriptions();
    }



    @GetMapping("user/{id}/subscription")
    public Subscription getUserSubscriptions(@PathVariable() Uid id) {
        return subscriptionService.getUserSubscription(id.getLocalId());
    }

}
