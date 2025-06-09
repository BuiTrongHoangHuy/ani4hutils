package site.ani4h.auth.payment;

import org.springframework.http.ResponseEntity;
import site.ani4h.auth.payment.entity.PaymentRequest;
import site.ani4h.auth.payment.entity.PaymentResponse;
import site.ani4h.auth.payment.entity.VNPayResponse;

import java.util.Map;

public interface PaymentService {

    PaymentResponse createPayment(PaymentRequest request);

    void verifyPayment(Map<String, String> response);
}
