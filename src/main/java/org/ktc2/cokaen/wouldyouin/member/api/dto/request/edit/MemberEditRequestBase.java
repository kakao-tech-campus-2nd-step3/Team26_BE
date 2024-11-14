package org.ktc2.cokaen.wouldyouin.member.api.dto.request.edit;

import jakarta.annotation.Nullable;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@EqualsAndHashCode
@ToString
@RequiredArgsConstructor
public abstract class MemberEditRequestBase {

    @Nullable private final String nickname;
    @Nullable private final String phoneNumber;
    @Nullable private final Long profileImageId;
}
