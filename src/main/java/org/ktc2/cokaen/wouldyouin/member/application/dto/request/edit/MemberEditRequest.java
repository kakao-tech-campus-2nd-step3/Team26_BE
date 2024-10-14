package org.ktc2.cokaen.wouldyouin.member.application.dto.request.edit;

import jakarta.annotation.Nullable;
import lombok.Builder;
import lombok.Getter;
import org.ktc2.cokaen.wouldyouin._common.persist.Area;

@Getter
public class MemberEditRequest extends MemberEditRequestBase {

    @Nullable private final Area area;

    @Builder
    public MemberEditRequest(@Nullable String nickname, @Nullable String phoneNumber, @Nullable String profileUrl, @Nullable Area area) {
        super(nickname, phoneNumber, profileUrl);
        this.area = area;
    }
}
