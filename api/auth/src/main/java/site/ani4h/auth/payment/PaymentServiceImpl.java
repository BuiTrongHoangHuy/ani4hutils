package site.ani4h.auth.payment;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import site.ani4h.auth.payment.entity.PaymentRequest;
import site.ani4h.auth.payment.entity.PaymentResponse;
import site.ani4h.auth.payment.entity.VNPayResponse;
import site.ani4h.auth.subscription.SubscriptionRepository;
import site.ani4h.auth.subscription.entity.SubscriptionRequest;
import site.ani4h.shared.common.Uid;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;

@Slf4j
@Service
public class PaymentServiceImpl implements PaymentService {
    private final SubscriptionRepository repository;

    public PaymentServiceImpl(SubscriptionRepository repository) {
        this.repository = repository;
    }
    @Override
    public PaymentResponse createPayment(PaymentRequest request) {

        String vnp_Version = "2.1.0";
        String vnp_Command = "pay";
        String vnp_TxnRef = VNPayConfig.getRandomNumber(8);
        String vnp_IpAddr = "127.0.0.1";
        String vnp_TmnCode = VNPayConfig.vnp_TmnCode;
        String orderType = "order-type";

        Map<String, String> vnp_Params = new HashMap<>();
        vnp_Params.put("vnp_Version", vnp_Version);
        vnp_Params.put("vnp_Command", vnp_Command);
        vnp_Params.put("vnp_TmnCode", vnp_TmnCode);
        vnp_Params.put("vnp_Amount", String.valueOf(request.getPrice()*100));
        vnp_Params.put("vnp_CurrCode", "VND");

        vnp_Params.put("vnp_TxnRef", vnp_TxnRef);
        vnp_Params.put("vnp_OrderInfo", request.getUserId().toString());
        vnp_Params.put("vnp_OrderType", orderType);

        String locate = "vn";
        vnp_Params.put("vnp_Locale", locate);

        String urlReturn = VNPayConfig.vnp_Returnurl;
        vnp_Params.put("vnp_ReturnUrl", urlReturn);
        vnp_Params.put("vnp_IpAddr", vnp_IpAddr);

        Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String vnp_CreateDate = formatter.format(cld.getTime());
        vnp_Params.put("vnp_CreateDate", vnp_CreateDate);

        cld.add(Calendar.MINUTE, 15);
        String vnp_ExpireDate = formatter.format(cld.getTime());
        vnp_Params.put("vnp_ExpireDate", vnp_ExpireDate);

        List fieldNames = new ArrayList(vnp_Params.keySet());
        Collections.sort(fieldNames);
        StringBuilder hashData = new StringBuilder();
        StringBuilder query = new StringBuilder();
        Iterator itr = fieldNames.iterator();
        while (itr.hasNext()) {
            String fieldName = (String) itr.next();
            String fieldValue = (String) vnp_Params.get(fieldName);
            if ((fieldValue != null) && (fieldValue.length() > 0)) {
                //Build hash data
                hashData.append(fieldName);
                hashData.append('=');
                try {
                    hashData.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                    //Build query
                    query.append(URLEncoder.encode(fieldName, StandardCharsets.US_ASCII.toString()));
                    query.append('=');
                    query.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                if (itr.hasNext()) {
                    query.append('&');
                    hashData.append('&');
                }
            }
        }
        String queryUrl = query.toString();
        String vnp_SecureHash = VNPayConfig.hmacSHA512(VNPayConfig.vnp_HashSecret, hashData.toString());
        queryUrl += "&vnp_SecureHash=" + vnp_SecureHash;
        String paymentUrl = VNPayConfig.vnp_PayUrl + "?" + queryUrl;

        log.info("Payment URL: {}", paymentUrl);
        PaymentResponse response = new PaymentResponse();
        response.setUrlPayment(paymentUrl);
        return response;
    }

    @Override
    public void verifyPayment(Map<String, String> allParams) {
        log.info("Payment verification started with params: {}", allParams);

        String vnp_SecureHash = allParams.get("vnp_SecureHash");
        allParams.remove("vnp_SecureHash");
        allParams.remove("vnp_SecureHashType");

        String signValue = VNPayConfig.hashAllFields(allParams);


        if (vnp_SecureHash.equals(signValue)) {
            log.info("Payment verification successful.");
            if ("00".equals(allParams.get("vnp_TransactionStatus"))) {
                SubscriptionRequest request = new SubscriptionRequest();
                request.setName("Premium");
                request.setPrice(100000);
                request.setQuality("Fair");
                request.setResolution("1080p");
                request.setMaxSimultaneousStreams(2);
                request.setUserId(new Uid(allParams.get("vnp_OrderInfo")));
                repository.createSubscription(request);
            } else {
                log.error("Payment failed.");
            }
        } else {
            log.error("Payment verification failed. Secure hash mismatch.");
        }
    }
}
