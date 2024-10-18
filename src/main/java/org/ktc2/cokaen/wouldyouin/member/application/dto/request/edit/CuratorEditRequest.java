package org.ktc2.cokaen.wouldyouin.member.application.dto.request.edit;

import io.micrometer.common.lang.Nullable;
import java.util.List;
import lombok.Getter;
import org.ktc2.cokaen.wouldyouin._common.persist.Area;

@Getter
public class CuratorEditRequest extends MemberEditRequest {

    @Nullable private final String intro;

    public CuratorEditRequest(@Nullable String nickname, @Nullable String phoneNumber, @Nullable List<Long> profileImage, @Nullable Area area, @Nullable String intro) {
        super(nickname, phoneNumber, profileImage, area);
        this.intro = intro;
    }
}
