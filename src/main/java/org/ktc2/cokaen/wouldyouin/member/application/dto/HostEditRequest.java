package org.ktc2.cokaen.wouldyouin.member.application.dto;


import jakarta.annotation.Nullable;
import lombok.Builder;
import lombok.Getter;

@Getter
public class HostEditRequest extends MemberEditRequestBase {

    @Nullable private final String intro;
    @Nullable  private final String hashtag;

    @Builder
    public HostEditRequest(@Nullable String nickname, @Nullable String phoneNumber, @Nullable String profileUrl,
        @Nullable String intro, @Nullable String hashtag) {
        super(nickname, phoneNumber, profileUrl);
        this.intro = intro;
        this.hashtag = hashtag;
    }
}
