package org.ktc2.cokaen.wouldyouin.controller.member.dto;

import jakarta.annotation.Nullable;
import lombok.Builder;
import lombok.Getter;
import org.ktc2.cokaen.wouldyouin.domain.Area;

@Getter
@Builder
public class MemberEditRequest extends MemberEditRequestBase {

    @Nullable private final Area area;

    public MemberEditRequest(@Nullable String nickname, @Nullable String phoneNumber, @Nullable String profileUrl, @Nullable Area area) {
        super(nickname, phoneNumber, profileUrl);
        this.area = area;
    }
}
