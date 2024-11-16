package org.ktc2.cokaen.wouldyouin.like.api.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode
@ToString
@Builder
public class LikeToggleResponse {

    private final boolean isLiked;

    public static LikeToggleResponse from(boolean state) {
        return LikeToggleResponse.builder()
            .isLiked(state)
            .build();
    }
}
