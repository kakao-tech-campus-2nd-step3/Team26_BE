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
    protected Area area;
    protected String gender;
    protected String profileImageUrl;

    @Builder
    protected MemberCreateRequest(String nickname, String email, String phone, AccountType accountType, String socialId, Area area, String gender, String profileImageUrl) {
        super(nickname, email, phone);
        this.accountType = accountType;
        this.socialId = socialId;
        this.area = area;
        this.gender = gender;
        this.profileImageUrl = profileImageUrl;
    }

    public Member toEntity() {
        return Member.builder()
            .nickname(this.nickname)
            .email(this.email)
            .phone(this.phone)
            .accountType(this.accountType)
            .socialId(this.socialId)
            .area(this.area)
            .gender(this.gender)
            .profileImageUrl(this.profileImageUrl)
            .build();
    }
}
