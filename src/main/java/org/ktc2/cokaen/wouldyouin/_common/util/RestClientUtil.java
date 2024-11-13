package org.ktc2.cokaen.wouldyouin._common.util;

import java.util.function.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClient.ResponseSpec.ErrorHandler;

@Component
@RequiredArgsConstructor
public class RestClientUtil {

    private final RestClient client;

    public <T> T get(Class<T> classType, String url, HttpHeaders headers, ErrorHandler errorHandler) {
        return client.get()
            .uri(url)
            .headers(httpHeaders -> httpHeaders.putAll(headers))
            .retrieve()
            .onStatus(Predicate.not(HttpStatusCode::is2xxSuccessful), errorHandler)
            .body(classType);
    }

    public <T> ResponseEntity<T> getResponseEntity(Class<T> classType, String url, HttpHeaders headers, ErrorHandler errorHandler) {
        return client.get()
            .uri(url)
            .headers(httpHeaders -> httpHeaders.putAll(headers))
            .retrieve()
            .onStatus(Predicate.not(HttpStatusCode::is2xxSuccessful), errorHandler)
            .toEntity(classType);
    }

    public <T> T post( Class<T> classType, String url, HttpHeaders headers, ErrorHandler errorHandler) {
        return client.post()
            .uri(url)
            .headers(httpHeaders -> httpHeaders.addAll(headers))
            .retrieve()
            .onStatus(Predicate.not(HttpStatusCode::is2xxSuccessful), errorHandler)
            .body(classType);
    }

    public <T, B> T post(Class<T> classType, String url, HttpHeaders headers, B body, ErrorHandler errorHandler) {
        return client.post()
            .uri(url)
            .headers(httpHeaders -> httpHeaders.addAll(headers))
            .body(body)
            .retrieve()
            .onStatus(Predicate.not(HttpStatusCode::is2xxSuccessful), errorHandler)
            .body(classType);
    }
}