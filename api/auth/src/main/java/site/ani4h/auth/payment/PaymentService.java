package site.ani4h.auth.payment;

import org.springframework.http.ResponseEntity;
import site.ani4h.auth.payment.entity.PaymentRequest;
import site.ani4h.auth.payment.entity.PaymentResponse;

public interface PaymentService {

    PaymentResponse createPayment(PaymentRequest request);

}
