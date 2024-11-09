package org.ktc2.cokaen.wouldyouin.payment.application;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import org.ktc2.cokaen.wouldyouin._common.exception.FailedToPayException;
import org.ktc2.cokaen.wouldyouin.payment.dto.KakaoPayRequest;
import org.ktc2.cokaen.wouldyouin.payment.dto.KakaoPayResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class PaymentService {

    // Todo: pay 취소 기능 추가
    // Todo: RestClient 관련 로직 유틸로 분리
    private final RestClient client = RestClient.builder().build();

    @Value("${oauth.payment.kakao_pay_request_host}")
    private String kakaoPayRequestHost;

    @Value("${oauth.payment.kakao_pay_single_payment_url}")
    private String kakaoPaySinglePaymentUrl;

    @Value("${oauth.payment.approval_url}")
    private String approvalUrl;

    @Value("${oauth.payment.cancel_url}")
    private String cancelUrl;

    @Value("${oauth.payment.fail_url}")
    private String failUrl;

    @Value("${oauth.payment.secret_key}")
    private String secretKey;

    public KakaoPayResponse createPayment(KakaoPayRequest kakaoPayRequest) {
        try {
            return client.post()
                .uri(URI.create("https://" + kakaoPayRequestHost + kakaoPaySinglePaymentUrl))
                .headers(httpHeaders -> httpHeaders.addAll(createKakaoPayRequestHeaders()))
                .body(createKakaoPayRequestBody(kakaoPayRequest))
                .retrieve()
                .body(KakaoPayResponse.class);
        } catch (Exception ex) {
            throw new FailedToPayException("결제에 실패했습니다.");
        }
    }

    private HttpHeaders createKakaoPayRequestHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Host", kakaoPayRequestHost);
        headers.add("Authorization", "SECRET_KEY " + secretKey);
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }

    private Map<String, String> createKakaoPayRequestBody(KakaoPayRequest kakaoPayRequest) {
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
