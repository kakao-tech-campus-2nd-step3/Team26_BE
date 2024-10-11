package org.ktc2.cokaen.wouldyouin.member.application.dto;

import jakarta.annotation.Nullable;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public abstract class MemberEditRequestBase {

    @Nullable private final String nickname;
    @Nullable private final String phoneNumber;
    @Nullable private final String profileUrl;
}
