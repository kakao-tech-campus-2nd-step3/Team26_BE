package org.ktc2.cokaen.wouldyouin.auth.application.oauth;

import com.fasterxml.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.annotation.PostConstruct;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ktc2.cokaen.wouldyouin._common.util.RestClientUtil;
import org.ktc2.cokaen.wouldyouin._common.util.UriUtil;
import org.ktc2.cokaen.wouldyouin.auth.application.oauth.dto.AccessTokenResponse;
import org.ktc2.cokaen.wouldyouin.auth.application.oauth.dto.OauthRequest;
import org.ktc2.cokaen.wouldyouin.auth.application.oauth.dto.OauthResourcesResponse;
import org.ktc2.cokaen.wouldyouin.auth.exception.FailAccessTokenGetException;
import org.ktc2.cokaen.wouldyouin.auth.exception.FailSocialDataGetException;
import org.ktc2.cokaen.wouldyouin.member.persist.AccountType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
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
    private String loginRequestUri;
    private String accessRequestUri;
    private HttpHeaders loginRequestHeaders;

    @PostConstruct
    private void init() {
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
    public OauthResourcesResponse getOauthMemberResources(String code) {
        return requestLoginAndAccessResources(OauthRequest.builder()
            .grantType("authorization_code")
            .clientId(clientId)
            .clientSecret(clientSecret)
            .redirectUri(redirectUri)
            .code(code)
            .build());
    }

    @Override
    protected OauthResourcesResponse requestLoginAndAccessResources(OauthRequest request) {

        AccessTokenResponse authenticationResponse = client.post(
            AccessTokenResponse.class, loginRequestUri, loginRequestHeaders, request,
            (req, response) -> { throw new FailAccessTokenGetException("구글 액세스 토큰을 가져오는데 실패했습니다."); });

        Objects.requireNonNull(authenticationResponse);
        GoogleAccessRequestResponse result = client.get(
            GoogleAccessRequestResponse.class, accessRequestUri, getAccessRequestHeaders(authenticationResponse),
            (req, rsp) -> { throw new FailSocialDataGetException("구글 소셜 계정 정보를 가져오는데 실패했습니다."); });

        log.debug("#### GoogleAccessRequestResponse result = {}", result);

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