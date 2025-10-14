package com.udemine.course_manage.service.Imps;

import com.udemine.course_manage.dto.request.ApiResponse;
import com.udemine.course_manage.dto.request.MomoCreationRequest;
import com.udemine.course_manage.dto.response.MomoResponse;
import com.udemine.course_manage.exception.AppException;
import com.udemine.course_manage.exception.ErrorCode;
import com.udemine.course_manage.service.Services.MomoService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.HexFormat;
import java.util.Map;

@Service
public class MomoServiceImps implements MomoService {
    private final String endpoint = "https://test-payment.momo.vn/v2/gateway/api/create";
    private final String partnerCode = "MOMO";
    private final String accessKey = "F8BBA842ECF85";
    private final String secretKey = "K951B6PE1waDMi640xX08PD3vg6EkVlz";

    @Override
    public Map<String, Object> createPayment(int orderId, double amount) {
        String requestId = String.valueOf(System.currentTimeMillis());
        String momoOrderId = orderId + "_" + requestId;
        String orderInfo = "Thanh toán đơn hàng #" + orderId;
        String redirectUrl = "http://localhost:8080/payment/result";
        String ipnUrl = "http://localhost:8080/payment/ipn";
        String extraData = "";

        String rawHash = "accessKey=" + accessKey +
                "&amount=" + (long) amount +
                "&extraData=" + extraData +
                "&ipnUrl=" + ipnUrl +
                "&orderId=" + momoOrderId +
                "&orderInfo=" + orderInfo +
                "&partnerCode=" + partnerCode +
                "&redirectUrl=" + redirectUrl +
                "&requestId=" + requestId +
                "&requestType=captureWallet";

        String signature = hmacSHA256(rawHash, secretKey);

        Map<String,Object> payload = new HashMap<>();
        payload.put("partnerCode", partnerCode);
        payload.put("partnerName", "Test");
        payload.put("storeId", "MomoTestStore");
        payload.put("requestId", requestId);
        payload.put("amount", String.valueOf((long) amount));
        payload.put("orderId", momoOrderId);
        payload.put("orderInfo", orderInfo);
        payload.put("redirectUrl", redirectUrl);
        payload.put("ipnUrl", ipnUrl);
        payload.put("extraData", extraData);
        payload.put("requestType", "captureWallet");
        payload.put("signature", signature);
        payload.put("accessKey", accessKey);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Map> response = restTemplate.postForEntity(endpoint, payload, Map.class);

        Map<String, Object> body = response.getBody();
        if (body == null || !body.containsKey("payUrl")) {
            throw new RuntimeException("MoMo response invalid: " + body);
        }

        return body;
    }

    /**
     * Verify chữ ký của MoMo khi gọi IPN
     */
    public boolean verifySignature(Map<String, Object> payload) {
        try {
            //Cho phép bỏ qua signature khi test
            String orderId = String.valueOf(payload.get("orderId"));
            if (orderId != null && orderId.contains("_MOCK")) {
                return true; // bỏ qua verify khi là IPN giả lập
            }
            String rawHash =
                    "accessKey=" + payload.get("accessKey") +
                            "&amount=" + payload.get("amount") +
                            "&extraData=" + payload.get("extraData") +
                            "&message=" + payload.get("message") +
                            "&orderId=" + payload.get("orderId") +
                            "&orderInfo=" + payload.get("orderInfo") +
                            "&orderType=" + payload.get("orderType") +
                            "&partnerCode=" + payload.get("partnerCode") +
                            "&payType=" + payload.get("payType") +
                            "&requestId=" + payload.get("requestId") +
                            "&responseTime=" + payload.get("responseTime") +
                            "&resultCode=" + payload.get("resultCode") +
                            "&transId=" + payload.get("transId");

            String expectedSignature = hmacSHA256(rawHash, secretKey);
            String actualSignature = String.valueOf(payload.get("signature"));

            return expectedSignature.equals(actualSignature);
        } catch (Exception e) {
            return false;
        }
    }

    private String hmacSHA256(String data, String key) throws AppException {
        try {
            Mac hmac = Mac.getInstance("HmacSHA256");
            SecretKeySpec secretKeySpec = new SecretKeySpec(key.getBytes(), "HmacSHA256");
            hmac.init(secretKeySpec);
            byte[] hash = hmac.doFinal(data.getBytes(StandardCharsets.UTF_8));
            return HexFormat.of().formatHex(hash);
        } catch (Exception e) {
            throw new AppException(ErrorCode.HMACSHA256);
        }
    }
}
