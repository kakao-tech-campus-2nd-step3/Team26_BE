package org.ktc2.cokaen.wouldyouin.member.api.dto.request.create;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.ktc2.cokaen.wouldyouin._common.vo.Area;
import org.ktc2.cokaen.wouldyouin.image.persist.MemberImage;
import org.ktc2.cokaen.wouldyouin.member.persist.AccountType;
import org.ktc2.cokaen.wouldyouin.member.persist.Member;

@Getter
@EqualsAndHashCode(callSuper = true)
@ToString
@RequiredArgsConstructor
public class MemberCreateRequest extends MemberCreateRequestBase {

    protected AccountType accountType;
    protected String socialId;
    protected String profileImageUrl;

    @Builder
    protected MemberCreateRequest(String nickname, String email, AccountType accountType, String socialId, String profileImageUrl) {
        super(nickname, email);
        this.accountType = accountType;
        this.socialId = socialId;
        this.profileImageUrl = profileImageUrl;
    }

    public Member toEntity(MemberImage profileImage, String thumbnailImageUrl) {
        return Member.builder()
            .nickname(this.nickname)
            .email(this.email)
            .phone("")
            .accountType(this.accountType)
            .socialId(this.socialId)
            .area(Area.서울)
            .gender("")
            .profileImage(profileImage)
            .profileImageThumbnailUrl(thumbnailImageUrl)
            .build();
    }
}
