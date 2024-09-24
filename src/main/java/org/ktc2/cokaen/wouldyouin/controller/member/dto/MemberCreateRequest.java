package org.ktc2.cokaen.wouldyouin.controller.member.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.ktc2.cokaen.wouldyouin.domain.Area;
import org.ktc2.cokaen.wouldyouin.domain.member.Member;

@Getter
@RequiredArgsConstructor
@Builder
public class MemberCreateRequest {

    private final String nickname;
    private final Area area;

    //TODO: 생성할 때 넣어야만 하는 정보 파악하기
    public Member toEntity() {
        return Member.builder()
            .nickname(this.nickname)
            .area(this.area)
            .build();
    }
}
