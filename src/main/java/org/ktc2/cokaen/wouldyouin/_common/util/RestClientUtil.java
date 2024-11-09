package org.ktc2.cokaen.wouldyouin._common.util;

import java.net.URI;
import lombok.RequiredArgsConstructor;
import org.ktc2.cokaen.wouldyouin.payment.dto.KakaoPayResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
@RequiredArgsConstructor
public class RestClientUtil {

    private final RestClient client;

    public <T> ResponseEntity<T> get(String url, Class<T> classType) {
        return client.get()
            .uri(url)
            .retrieve()
            .toEntity(classType);
    }

    public <T, B> ResponseEntity<T> post(String url, HttpHeaders headers, B body, Class<T> classType) {
        return client.post()
            .uri(url)
            .headers(httpHeaders -> httpHeaders.addAll(headers))
            .body(body)
            .retrieve()
            .toEntity(classType);
    }
}