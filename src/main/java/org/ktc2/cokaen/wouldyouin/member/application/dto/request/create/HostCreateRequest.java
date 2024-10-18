package org.ktc2.cokaen.wouldyouin.member.application.dto.request.create;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.ktc2.cokaen.wouldyouin.member.persist.Host;
import org.springframework.security.crypto.password.PasswordEncoder;

@Getter
@RequiredArgsConstructor
public class HostCreateRequest extends MemberCreateRequestBase {

    protected String phone;
    protected String password;

    public HostCreateRequest(String nickname, String email, String phone, String password) {
        super(nickname, email);
        this.phone = phone;
        this.password = password;
    }

    public Host toEntity(PasswordEncoder passwordEncoder) {
        return Host.builder()
            .nickname(this.nickname)
            .email(this.email)
            .phone(this.phone)
            .hashedPassword(passwordEncoder.encode(this.password))
            .build();
    }
}
