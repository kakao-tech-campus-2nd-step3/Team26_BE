package org.ktc2.cokaen.wouldyouin.auth.application.oauth.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
@Builder(toBuilder = true)
@JsonNaming(SnakeCaseStrategy.class)
public class OauthRequest {

    private final String grantType;
    private final String clientId;
    private final String clientSecret;
    private final String redirectUri;
    private final String code;
}
