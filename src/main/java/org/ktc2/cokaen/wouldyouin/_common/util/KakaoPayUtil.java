package org.ktc2.cokaen.wouldyouin._common.util;

import java.util.HashMap;
import java.util.Map;
import org.ktc2.cokaen.wouldyouin.payment.dto.KakaoPayRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

public class KakaoPayUtil {

    public static HttpHeaders createKakaoPayRequestHeaders(String kakaoPayRequestHost, String secretKey) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Host", kakaoPayRequestHost);
        headers.add("Authorization", "SECRET_KEY " + secretKey);
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }

    public static Map<String, String> createKakaoPayRequestBody(KakaoPayRequest kakaoPayRequest, String approvalUrl, String cancelUrl, String failUrl) {
        Map<String, String> body = new HashMap<>();
        body.put("cid", "TC0ONETIME");
        body.put("partner_order_id", kakaoPayRequest.getReservationId());
        body.put("partner_user_id", kakaoPayRequest.getHostId());
        body.put("item_name", kakaoPayRequest.getEventName());
        body.put("quantity", kakaoPayRequest.getQuantity());
        body.put("total_amount", kakaoPayRequest.getTotalAmount());
        body.put("tax_free_amount", kakaoPayRequest.getTaxFreeAmount());
        body.put("approval_url", approvalUrl);
        body.put("cancel_url", cancelUrl);
        body.put("fail_url", failUrl);
        return body;
    }
}