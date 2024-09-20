package org.ktc2.cokaen.wouldyouin.controller.member;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.ktc2.cokaen.wouldyouin.domain.Area;
import org.ktc2.cokaen.wouldyouin.domain.Member;

@Getter
@RequiredArgsConstructor
public class MemberCreateRequest {

    private final String nickname;
    private final Area area;

    public Member toEntity() {
        return Member.builder()
            .nickname(this.nickname)
            .area(this.area)
            .build();
    }
}
