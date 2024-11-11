package org.ktc2.cokaen.wouldyouin.auth.application.oauth;

import com.fasterxml.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import java.util.Objects;
import org.ktc2.cokaen.wouldyouin._common.util.RestClientUtil;
import org.ktc2.cokaen.wouldyouin._common.util.UriUtil;
import org.ktc2.cokaen.wouldyouin.auth.application.oauth.dto.AccessTokenResponse;
import org.ktc2.cokaen.wouldyouin.auth.application.oauth.dto.OauthRequest;
import org.ktc2.cokaen.wouldyouin.auth.application.oauth.dto.OauthResourcesResponse;
import org.ktc2.cokaen.wouldyouin.member.persist.AccountType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

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

    private final RestClientUtil client;
    private final String loginRequestUri;
    private final String accessRequestUri;
    private final HttpHeaders loginRequestHeaders;

    public GoogleRequestService(RestClientUtil restClientUtil) {
        this.client = restClientUtil;

        loginRequestUri = UriUtil.buildUrl("https", loginRequestHost, loginRequestPath);
        accessRequestUri = UriUtil.buildUrl("https", accessRequestHost, accessRequestPath);

        loginRequestHeaders = new HttpHeaders();
        loginRequestHeaders.add(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
    }

    protected HttpHeaders getAccessRequestHeaders(AccessTokenResponse authenticationResponse) {
        HttpHeaders accessRequestHeaders = new HttpHeaders();
        accessRequestHeaders.add(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
        accessRequestHeaders.add(HttpHeaders.AUTHORIZATION, "Bearer " + authenticationResponse.getAccessToken());
        return accessRequestHeaders;
    }

    @Override
    protected AccountType getAccountType() {
        return AccountType.google;
    }

    @Override
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

        AccessTokenResponse authenticationResponse = client.post(
            AccessTokenResponse.class, loginRequestUri, loginRequestHeaders, request,
            // TODO: 커스텀 예외 추가
            (req, response) -> { throw new RuntimeException("에러"); });

        Objects.requireNonNull(authenticationResponse);
        GoogleAccessRequestResponse result = client.get(
            GoogleAccessRequestResponse.class, accessRequestUri, getAccessRequestHeaders(authenticationResponse),
            // TODO: 커스텀 예외 추가
            (req, rsp) -> { throw new RuntimeException("에러"); });

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