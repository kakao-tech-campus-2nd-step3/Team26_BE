package org.ktc2.cokaen.wouldyouin.member.application.dto.request.create;

import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.ktc2.cokaen.wouldyouin.Image.persist.MemberImage;
import org.ktc2.cokaen.wouldyouin._common.persist.Area;
import org.ktc2.cokaen.wouldyouin.member.persist.AccountType;
import org.ktc2.cokaen.wouldyouin.member.persist.Member;

@Getter
@RequiredArgsConstructor
public class MemberCreateRequest extends MemberCreateRequestBase {

    protected AccountType accountType;
    protected String socialId;
    protected List<MemberImage> profileImage;

    @Builder
    protected MemberCreateRequest(String nickname, String email, AccountType accountType, String socialId, List<MemberImage> profileImage) {
        super(nickname, email);
        this.accountType = accountType;
        this.socialId = socialId;
        this.profileImage = profileImage;
    }

    public Member toEntity() {
        return Member.builder()
            .nickname(this.nickname)
            .email(this.email)
            .phone("")
            .accountType(this.accountType)
            .socialId(this.socialId)
            .area(Area.서울)
            .gender("")
            .profileImage(this.profileImage)
            .build();
    }
}
