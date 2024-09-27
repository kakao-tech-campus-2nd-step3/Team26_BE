package org.ktc2.cokaen.wouldyouin.controller.member;

import jakarta.annotation.Nullable;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.ktc2.cokaen.wouldyouin.domain.Area;

@Getter
@RequiredArgsConstructor
public class MemberEditRequest {

    @Nullable private final String nickname;
    @Nullable private final Area area;
    @Nullable private final String phoneNumber;
    @Nullable private final String profileUrl;
    @Nullable private final String intro;
    @Nullable private final String hashtag;
    @Nullable private final String curatorProfileUrl;
}
