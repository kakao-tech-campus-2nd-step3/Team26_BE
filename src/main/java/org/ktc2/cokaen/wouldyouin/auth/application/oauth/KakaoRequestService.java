package org.ktc2.cokaen.wouldyouin.auth.application.oauth;

import com.fasterxml.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.annotation.PostConstruct;
import java.util.Objects;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
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
import org.springframework.util.LinkedMultiValueMap;

@Slf4j
@Service
@RequiredArgsConstructor
public class KakaoRequestService extends OauthRequestService {

    @Value("${oauth.kakao.uri.login.host}")
    private String loginRequestHost;

    @Value("${oauth.kakao.uri.login.path}")
    private String loginRequestPath;

    @Value("${oauth.kakao.uri.access.host}")
    private String accessRequestHost;

    @Value("${oauth.kakao.uri.access.path}")
    private String accessRequestPath;

    @Value("${oauth.kakao.client.id}")
    private String clientId;

    @Value("${oauth.kakao.client.secret}")
    private String clientSecret;

    @Value("${oauth.kakao.redirect_uri}")
    private String redirectUri;

    @Override
    protected AccountType getAccountType() {
        return AccountType.kakao;
    }

    private final RestClientUtil client;
    private String accessRequestUri;
    private HttpHeaders loginRequestHeaders;

    @PostConstruct
    private void init() {
        accessRequestUri = UriUtil.buildUrl("https", accessRequestHost, accessRequestPath);
        loginRequestHeaders = new HttpHeaders();
        loginRequestHeaders.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE);
    }

    protected String getLoginRequestUri(OauthRequest request) {
        var queries = new LinkedMultiValueMap<String, String>();
        queries.add("grant_type", request.getGrantType());
        queries.add("client_id", request.getClientId());
        queries.add("client_secret", request.getClientSecret());
        queries.add("code", request.getCode());
        return UriUtil.buildUrl("https", loginRequestHost, loginRequestPath, queries);
    }

    protected HttpHeaders getAccessRequestHeaders(AccessTokenResponse authenticationResponse) {
        HttpHeaders accessRequestHeaders = new HttpHeaders();
        accessRequestHeaders.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE);
        accessRequestHeaders.add(HttpHeaders.AUTHORIZATION, "Bearer " + authenticationResponse.getAccessToken());
        return accessRequestHeaders;
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
            AccessTokenResponse.class, getLoginRequestUri(request), loginRequestHeaders,
            (req, rsp) -> { throw new FailAccessTokenGetException("카카오 액세스 토큰을 가져오는데 실패했습니다."); });

        Objects.requireNonNull(authenticationResponse);
        KakaoAccessRequestResponse result = client.get(
            KakaoAccessRequestResponse.class, accessRequestUri, getAccessRequestHeaders(authenticationResponse),
            (req, rsp) -> { throw new FailSocialDataGetException("카카오 소셜 계정 정보를 가져오는데 실패했습니다."); });

        log.debug("#### KakaoAccessRequestResponse result = {}", result);

        Objects.requireNonNull(result);
        return OauthResourcesResponse.builder()
            .socialId(result.getId().toString())
            .nickname(result.getKakaoAccount().getProfile().getNickname())
            .profileImageUrl(result.getKakaoAccount().getProfile().getProfileImageUrl())
            .email(result.getKakaoAccount().getEmail())
            .build();
    }

    @JsonNaming(SnakeCaseStrategy.class)
    @RequiredArgsConstructor
    @Getter
    @ToString
    static class KakaoAccessRequestResponse {

        private final Long id;

        private final KakaoAccount kakaoAccount;

        @JsonNaming(SnakeCaseStrategy.class)
        @RequiredArgsConstructor
        @Getter
        @ToString
        static class KakaoAccount {

            private final Profile profile;

            private final String email;

            @JsonNaming(SnakeCaseStrategy.class)
            @RequiredArgsConstructor
            @Getter
            @ToString
            static class Profile {

                private final String nickname;

                private final String profileImageUrl;
            }
        }
    }
}