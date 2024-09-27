package org.ktc2.cokaen.wouldyouin.controller.member.dto;

import jakarta.annotation.Nullable;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@Builder
public abstract class MemberEditRequestBase {

    @Nullable private final String nickname;
    @Nullable private final String phoneNumber;
    @Nullable private final String profileUrl;
}
