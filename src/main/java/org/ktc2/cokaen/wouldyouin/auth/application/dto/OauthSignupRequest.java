package org.ktc2.cokaen.wouldyouin.auth.application.dto;

import lombok.RequiredArgsConstructor;
import org.ktc2.cokaen.wouldyouin._common.persist.Area;
import org.ktc2.cokaen.wouldyouin.member.application.dto.request.create.MemberCreateRequest;
import org.ktc2.cokaen.wouldyouin.member.persist.AccountType;

@RequiredArgsConstructor
public class OauthSignupRequest {

    private final String nickname;
    private final String email;
    private final String phone;
    private final String socialId;
    private final Area area;
    private final String gender;
    private final String profileImageUrl;

    public MemberCreateRequest toMemberCreateRequest(AccountType accountType) {
        return MemberCreateRequest.builder()
            .nickname(nickname)
            .email(email)
            .phone(phone)
            .socialId(socialId)
            .area(area)
            .gender(gender)
            .profileImageUrl(profileImageUrl)
            .accountType(accountType)
            .build();
    }
}
