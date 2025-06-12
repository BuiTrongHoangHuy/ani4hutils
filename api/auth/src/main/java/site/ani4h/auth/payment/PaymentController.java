package site.ani4h.auth.payment;


import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import site.ani4h.auth.payment.entity.PaymentRequest;
import site.ani4h.auth.payment.entity.PaymentResponse;
import site.ani4h.auth.payment.entity.VNPayResponse;

import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/v1")
public class PaymentController {
    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }
    @PostMapping("/payment/create")
    public ResponseEntity<PaymentResponse> createPaymentUrl(@RequestBody PaymentRequest request) {
        PaymentResponse payment = paymentService.createPayment(request);
        if (payment == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(payment);
    }

    @GetMapping("/payment/verify")
    public void verifyPayment(@RequestParam Map<String, String> allParams) {
        log.info("Payment URL: {}", allParams);


        paymentService.verifyPayment(allParams);

    }
}
