package org.ktc2.cokaen.wouldyouin.member.api.dto.request.edit;

import io.micrometer.common.lang.Nullable;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.ktc2.cokaen.wouldyouin._common.vo.Area;

@Getter
@EqualsAndHashCode(callSuper = true)
@ToString
public class CuratorEditRequest extends MemberEditRequest {

    @Nullable private final String intro;

    @Builder(builderMethodName = "curatorEditRequestBuilder")
    public CuratorEditRequest(@Nullable String nickname, @Nullable String phoneNumber, @Nullable Long profileImageId, @Nullable Area area, @Nullable String intro) {
        super(nickname, phoneNumber, profileImageId, area);
        this.intro = intro;
    }
}
