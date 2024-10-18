package org.ktc2.cokaen.wouldyouin.auth.application.dto;

import lombok.Builder;

@Builder
public record TokenResponse(String token) {

    public static TokenResponse from(String token) {
        return TokenResponse.builder()
            .token(token)
            .build();
    }
}
