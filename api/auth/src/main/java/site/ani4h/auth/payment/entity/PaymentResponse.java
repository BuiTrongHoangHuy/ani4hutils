package site.ani4h.auth.payment.entity;

import lombok.Getter;
import lombok.Setter;
import site.ani4h.auth.payment.VNPayConfig;
@Getter
@Setter
public class PaymentResponse {
    String urlPayment;

    public PaymentResponse() {
    }

    public PaymentResponse(String url) {
        this.urlPayment = url;
    }
}
