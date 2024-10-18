package org.ktc2.cokaen.wouldyouin.member.application.dto.request.edit;


import jakarta.annotation.Nullable;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
public class HostEditRequest extends MemberEditRequestBase {

    @Nullable private final String intro;
    @Nullable  private final String hashtag;

    @Builder
    public HostEditRequest(@Nullable String nickname, @Nullable String phoneNumber, @Nullable List<Long> profileImage,
        @Nullable String intro, @Nullable String hashtag) {
        super(nickname, phoneNumber, profileImage);
        this.intro = intro;
        this.hashtag = hashtag;
    }
}
