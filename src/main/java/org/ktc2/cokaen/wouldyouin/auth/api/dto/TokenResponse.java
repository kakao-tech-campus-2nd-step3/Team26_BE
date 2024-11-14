package org.ktc2.cokaen.wouldyouin.auth.api.dto;

import lombok.Builder;
import org.ktc2.cokaen.wouldyouin.member.persist.MemberType;

@Builder
public record TokenResponse(String token, Long memberId, MemberType memberType) {

    public static TokenResponse of(String token, Long memberId, MemberType memberType) {
        return TokenResponse.builder()
            .token(token)
            .memberId(memberId)
            .memberType(memberType)
            .build();
    }
}
