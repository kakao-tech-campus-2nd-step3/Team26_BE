package org.ktc2.cokaen.wouldyouin.like.application;

import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.ktc2.cokaen.wouldyouin.member.persist.LikeableMember;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class LikeResponse {

    private final Long memberId;
    private final String nickname;
    private final String intro;
    private final List<String> hashtags;
    private final String profileImageUrl;

    public static LikeResponse from(LikeableMember member) {
        return LikeResponse.builder()
            .memberId(member.getId())
            .nickname(member.getNickname())
            .intro(member.getIntro())
            .hashtags(member.getHashTagList())
            .profileImageUrl(member.getProfileImageUrl())
            .build();
    }
}
