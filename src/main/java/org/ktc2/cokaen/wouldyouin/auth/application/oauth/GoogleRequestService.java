package org.ktc2.cokaen.wouldyouin.auth.application.oauth;

import com.fasterxml.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import java.util.Objects;
import org.ktc2.cokaen.wouldyouin.auth.application.oauth.dto.AccessTokenResponse;
import org.ktc2.cokaen.wouldyouin.auth.application.oauth.dto.OauthRequest;
import org.ktc2.cokaen.wouldyouin.auth.application.oauth.dto.OauthResourcesResponse;
import org.ktc2.cokaen.wouldyouin.member.persist.AccountType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class GoogleRequestService extends OauthRequestService {

    @Value("${oauth.google.uri.login.host}")
    private String loginRequestHost;

    @Value("${oauth.google.uri.login.path}")
    private String loginRequestPath;

    @Value("${oauth.google.uri.access.host}")
    private String accessRequestHost;

    @Value("${oauth.google.uri.access.path}")
    private String accessRequestPath;

    @Value("${oauth.google.client.id}")
    private String clientId;

    @Value("${oauth.google.client.secret}")
    private String clientSecret;

    @Value("${oauth.google.redirect_uri}")
    private String redirectUri;

    @Override
    protected AccountType getAccountType() {
        return AccountType.google;
    }

    protected OauthRequest getOauthRequestBase() {
        return OauthRequest.builder()
            .grantType("authorization_code")
            .clientId(clientId)
            .clientSecret(clientSecret)
            .redirectUri(redirectUri)
            .build();
    }

    @Override
    protected OauthResourcesResponse requestLoginAndAccessResources(OauthRequest request) {
        AccessTokenResponse authenticationResponse = RestClient.create()
            .post()
            .uri(uriBuilder -> uriBuilder
                .scheme("https")
                .host(loginRequestHost)
                .path(loginRequestPath)
                .build(true))
            .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
            .body(request)
            .retrieve()
            // TODO: 커스텀 예외 추가
            .onStatus(HttpStatusCode::is4xxClientError, (httpRequest, clientHttpResponse) -> { throw new RuntimeException("에러"); })
            .onStatus(HttpStatusCode::is5xxServerError, (httpRequest, clientHttpResponse) -> { throw new RuntimeException("에러"); })
            .body(AccessTokenResponse.class);

        Objects.requireNonNull(authenticationResponse);
        GoogleAccessRequestResponse result = RestClient.create()
            .get()
            .uri(uriBuilder -> uriBuilder
                .scheme("https")
                .host(accessRequestHost)
                .path(accessRequestPath)
                .build(true))
            .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
            .header(HttpHeaders.AUTHORIZATION, "Bearer " + authenticationResponse.getAccessToken())
            .retrieve()
            // TODO: 커스텀 예외 추가
            .onStatus(HttpStatusCode::is4xxClientError, (httpRequest, clientHttpResponse) -> { throw new RuntimeException("에러"); })
            .onStatus(HttpStatusCode::is5xxServerError, (httpRequest, clientHttpResponse) -> { throw new RuntimeException("에러"); })
            .body(GoogleAccessRequestResponse.class);

        Objects.requireNonNull(result);
        return OauthResourcesResponse.builder()
            .socialId(result.id)
            .nickname(result.name)
            .profileImageUrl(result.picture)
            .email(result.email)
            .build();
    }

    @JsonNaming(SnakeCaseStrategy.class)
    record GoogleAccessRequestResponse(String id, String email, String name, String picture) {
    }
}