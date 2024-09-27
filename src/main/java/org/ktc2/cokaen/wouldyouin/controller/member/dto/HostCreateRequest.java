package org.ktc2.cokaen.wouldyouin.controller.member.dto;

import lombok.Builder;
import lombok.Getter;
import org.ktc2.cokaen.wouldyouin.domain.member.Host;

@Getter
@Builder
public record HostCreateRequest(String nickname) {

    //TODO: 생성할 때 넣어야만 하는 정보 파악하기
    public Host toEntity() {
        return Host.builder()
            .nickname(nickname)
            .build();
    }
}
