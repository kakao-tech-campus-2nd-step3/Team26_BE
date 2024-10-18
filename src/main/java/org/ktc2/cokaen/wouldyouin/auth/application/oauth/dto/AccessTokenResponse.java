package org.ktc2.cokaen.wouldyouin.auth.application.oauth.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@NoArgsConstructor(force = true)
@JsonNaming(SnakeCaseStrategy.class)
public class AccessTokenResponse {

    private final String accessToken;
}
