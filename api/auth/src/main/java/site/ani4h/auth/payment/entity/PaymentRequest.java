package site.ani4h.auth.payment.entity;

import lombok.Getter;
import lombok.Setter;
import site.ani4h.shared.common.Uid;
@Getter
@Setter
public class PaymentRequest {
    private Uid userId;
    private int price;
    private int status;
}
