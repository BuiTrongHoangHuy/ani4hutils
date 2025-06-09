package site.ani4h.auth.payment.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VNPayResponse {

    private String vnp_TmnCode;
    private String vnp_TransactionNo;
    private String vnp_TransactionStatus;
    private String vnp_ResponseCode;
    private String vnp_OrderInfo;
    private String vnp_PayDate;
    private String vnp_SecureHash;


    public VNPayResponse() {
    }
    public VNPayResponse(String vnp_TmnCode, String vnp_TransactionNo, String vnp_TransactionStatus, String vnp_ResponseCode, String vnp_OrderInfo, String vnp_PayDate, String vnp_SecureHash) {
        this.vnp_TmnCode = vnp_TmnCode;
        this.vnp_TransactionNo = vnp_TransactionNo;
        this.vnp_TransactionStatus = vnp_TransactionStatus;
        this.vnp_ResponseCode = vnp_ResponseCode;
        this.vnp_OrderInfo = vnp_OrderInfo;
        this.vnp_PayDate = vnp_PayDate;
        this.vnp_SecureHash = vnp_SecureHash;
    }
}
