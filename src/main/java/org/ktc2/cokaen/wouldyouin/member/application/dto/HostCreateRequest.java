package org.ktc2.cokaen.wouldyouin.member.application.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.ktc2.cokaen.wouldyouin.member.persist.Host;

@Getter
@RequiredArgsConstructor
@Builder
public class HostCreateRequest {

    private final String nickname;

    //TODO: 생성할 때 넣어야만 하는 정보 파악하기
    public Host toEntity() {
        return Host.builder()
            .nickname(nickname)
            .build();
    }
}
