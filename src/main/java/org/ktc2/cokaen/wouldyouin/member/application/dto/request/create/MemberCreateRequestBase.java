package org.ktc2.cokaen.wouldyouin.member.application.dto.request.create;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public abstract class MemberCreateRequestBase {

    protected String nickname;
    protected String email;

    protected MemberCreateRequestBase(String nickname, String email) {
        this.nickname = nickname;
        this.email = email;
    }
}
