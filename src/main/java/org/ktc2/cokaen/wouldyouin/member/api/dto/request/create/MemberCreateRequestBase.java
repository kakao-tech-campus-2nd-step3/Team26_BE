package org.ktc2.cokaen.wouldyouin.member.api.dto.request.create;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@RequiredArgsConstructor
@EqualsAndHashCode
@ToString
public abstract class MemberCreateRequestBase {

    protected String nickname;
    protected String email;

    protected MemberCreateRequestBase(String nickname, String email) {
        this.nickname = nickname;
        this.email = email;
    }
}
