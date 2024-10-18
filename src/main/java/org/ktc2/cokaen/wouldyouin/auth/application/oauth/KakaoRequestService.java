package org.ktc2.cokaen.wouldyouin.auth.application.oauth;

import com.fasterxml.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import java.util.Objects;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
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
                .queryParam("grant_type", request.getGrantType())
                .queryParam("client_id", request.getClientId())
                .queryParam("client_secret", request.getClientSecret())
                .queryParam("code", request.getCode())
                .build(true))
            .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE)
            .retrieve()
            // TODO: 커스텀 예외 추가
            .onStatus(HttpStatusCode::is4xxClientError, (httpRequest, clientHttpResponse) -> { throw new RuntimeException("에러"); })
            .onStatus(HttpStatusCode::is5xxServerError, (httpRequest, clientHttpResponse) -> { throw new RuntimeException("에러"); })
            .body(AccessTokenResponse.class);

        Objects.requireNonNull(authenticationResponse);
        KakaoAccessRequestResponse result = RestClient.create()
            .get()
            .uri(uriBuilder -> uriBuilder
                .scheme("https")
                .host(accessRequestHost)
                .path(accessRequestPath)
                .build(true))
            .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE)
            .header(HttpHeaders.AUTHORIZATION, "Bearer " + authenticationResponse.getAccessToken())
            .retrieve()
            // TODO: 커스텀 예외 추가
            .onStatus(HttpStatusCode::is4xxClientError, (httpRequest, clientHttpResponse) -> { throw new RuntimeException("에러"); })
            .onStatus(HttpStatusCode::is5xxServerError, (httpRequest, clientHttpResponse) -> { throw new RuntimeException("에러"); })
            .body(KakaoAccessRequestResponse.class);

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
    static class KakaoAccessRequestResponse {

        private final Long id;

        private final KakaoAccount kakaoAccount;

        @JsonNaming(SnakeCaseStrategy.class)
        @RequiredArgsConstructor
        @Getter
        static class KakaoAccount {

            private final Profile profile;

            private final String email;

            @JsonNaming(SnakeCaseStrategy.class)
            @RequiredArgsConstructor
            @Getter
            static class Profile {

                private final String nickname;

                private final String profileImageUrl;
            }
        }
    }
}