package org.ktc2.cokaen.wouldyouin.member.api.dto.relationResponse;

import lombok.Builder;
import lombok.Getter;
import org.ktc2.cokaen.wouldyouin.member.persist.Member;

@Getter
@Builder
public class ReviewMemberResponse {

    private Long memberId;
    private String nickname;

    public static ReviewMemberResponse from(Member member) {
        return builder()
            .memberId(member.getId())
            .nickname(member.getNickname())
            .build();
    }
}
