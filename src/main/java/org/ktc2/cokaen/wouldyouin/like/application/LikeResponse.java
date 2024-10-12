package org.ktc2.cokaen.wouldyouin.like.application;

import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class LikeResponse {

    private final String nickname;
    private final String intro;
    private final List<String> hashtags;
    private final String profileImageUrl;
}
