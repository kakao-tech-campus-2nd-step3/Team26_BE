package org.ktc2.cokaen.wouldyouin.member.application.dto.request.create;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.ktc2.cokaen.wouldyouin.member.persist.Host;

@Getter
@RequiredArgsConstructor
public class HostCreateRequest extends MemberCreateRequestBase {

    protected String password;

    public HostCreateRequest(String nickname, String email, String phone, String password) {
        super(nickname, email, phone);
        this.password = password;
    }

    public Host toEntity() {
        return Host.builder()
            .nickname(this.nickname)
            .email(this.email)
            .phone(this.phone)
            .hashedPassword(this.password)
            .build();
    }
}
