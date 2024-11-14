package org.ktc2.cokaen.wouldyouin.member.api.dto.relationResponse;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.ktc2.cokaen.wouldyouin.member.persist.Member;

@Getter
@Builder
@EqualsAndHashCode
@ToString
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
