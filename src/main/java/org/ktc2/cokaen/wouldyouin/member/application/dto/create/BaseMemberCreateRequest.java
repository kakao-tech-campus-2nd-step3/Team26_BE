package org.ktc2.cokaen.wouldyouin.member.application.dto.create;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public abstract class BaseMemberCreateRequest {

    protected String nickname;
    protected String email;
    protected String phone;

    protected BaseMemberCreateRequest(String nickname, String email, String phone) {
        this.nickname = nickname;
        this.email = email;
        this.phone = phone;
    }
}
