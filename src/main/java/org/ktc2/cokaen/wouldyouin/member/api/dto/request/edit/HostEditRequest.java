package org.ktc2.cokaen.wouldyouin.member.api.dto.request.edit;

import jakarta.annotation.Nullable;
import java.util.List;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@EqualsAndHashCode(callSuper = true)
@ToString
public class HostEditRequest extends MemberEditRequestBase {

    @Nullable private final String intro;
    @Nullable  private final List<String> hashtags;

    @Builder
    public HostEditRequest(@Nullable String nickname, @Nullable String phoneNumber, @Nullable Long profileImageId,
        @Nullable String intro, @Nullable List<String> hashtags) {
        super(nickname, phoneNumber, profileImageId);
        this.intro = intro;
        this.hashtags = hashtags;
    }
}
