package org.ktc2.cokaen.wouldyouin.member.application.dto;

import io.micrometer.common.lang.Nullable;
import lombok.Getter;
import org.ktc2.cokaen.wouldyouin._common.persist.Area;

@Getter
public class CuratorEditRequest extends MemberEditRequest {

    @Nullable private final String intro;

    public CuratorEditRequest(@Nullable String nickname, @Nullable String phoneNumber, @Nullable String profileUrl, @Nullable Area area, @Nullable String intro) {
        super(nickname, phoneNumber, profileUrl, area);
        this.intro = intro;
    }
}
