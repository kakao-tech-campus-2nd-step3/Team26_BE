package org.ktc2.cokaen.wouldyouin.member.application.dto.request.create;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.ktc2.cokaen.wouldyouin._common.persist.Area;
import org.ktc2.cokaen.wouldyouin.member.persist.AccountType;
import org.ktc2.cokaen.wouldyouin.member.persist.Member;

@Getter
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

    public Member toEntity() {
        return Member.builder()
            .nickname(this.nickname)
            .email(this.email)
            .phone("")
            .accountType(this.accountType)
            .socialId(this.socialId)
            .area(Area.서울)
            .gender("")
            .profileImageUrl(this.profileImageUrl)
            .build();
    }
}
