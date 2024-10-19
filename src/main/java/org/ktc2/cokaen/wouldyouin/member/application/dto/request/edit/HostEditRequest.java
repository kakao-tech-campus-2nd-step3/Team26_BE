package org.ktc2.cokaen.wouldyouin.member.application.dto.request.edit;


import jakarta.annotation.Nullable;
import lombok.Builder;
import lombok.Getter;

@Getter
public class HostEditRequest extends MemberEditRequestBase {

    @Nullable private final String intro;
    @Nullable  private final String hashtag;

    @Builder
    public HostEditRequest(@Nullable String nickname, @Nullable String phoneNumber, @Nullable Long profileImageId,
        @Nullable String intro, @Nullable String hashtag) {
        super(nickname, phoneNumber, profileImageId);
        this.intro = intro;
        this.hashtag = hashtag;
    }
}
