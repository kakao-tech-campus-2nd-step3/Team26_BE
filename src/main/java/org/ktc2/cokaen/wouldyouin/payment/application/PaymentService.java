package org.ktc2.cokaen.wouldyouin.payment.application;

import java.nio.file.Paths;
import lombok.RequiredArgsConstructor;
import org.ktc2.cokaen.wouldyouin._common.exception.FailedToPayException;
import org.ktc2.cokaen.wouldyouin._common.util.KakaoPayUtil;
import org.ktc2.cokaen.wouldyouin._common.util.RestClientUtil;
import org.ktc2.cokaen.wouldyouin.payment.dto.KakaoPayRequest;
import org.ktc2.cokaen.wouldyouin.payment.dto.KakaoPayResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final RestClientUtil client;

    @Value("${oauth.payment.kakao-pay-request-host}")
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
        return client.post(
            KakaoPayResponse.class,
            Paths.get(kakaoPayRequestHost, kakaoPaySinglePaymentUrl).toString(),
            KakaoPayUtil.createKakaoPayRequestHeaders(kakaoPayRequestHost, secretKey),
            KakaoPayUtil.createKakaoPayRequestBody(kakaoPayRequest, approvalUrl, cancelUrl, failUrl),
            (req, rsp) -> { throw new FailedToPayException("카카오페이 API 요청을 실패하였습니다."); }
        );
    }
    // Todo: pay 취소 기능 추가
}