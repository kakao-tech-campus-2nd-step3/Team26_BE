package org.ktc2.cokaen.wouldyouin.like.application.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.ktc2.cokaen.wouldyouin.member.persist.LikeableMember;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class LikeToggleResponse {
    private final boolean isLiked;

    public static LikeToggleResponse from(boolean state) {
        return LikeToggleResponse.builder()
            .isLiked(state)
            .build();
    }
}
