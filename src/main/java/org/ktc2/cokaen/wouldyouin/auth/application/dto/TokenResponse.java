package org.ktc2.cokaen.wouldyouin.auth.application.dto;

import lombok.Builder;
import org.ktc2.cokaen.wouldyouin.auth.application.JwtService;
import org.ktc2.cokaen.wouldyouin.member.application.dto.MemberResponse;

@Builder
public record TokenResponse(String token) {

    public static TokenResponse from(MemberResponse memberResponse, JwtService jwtService) {
        return TokenResponse.builder()
            .token(jwtService.createAccessToken(memberResponse.getMemberId(), memberResponse.getMemberType()))
            .build();
    }
}
