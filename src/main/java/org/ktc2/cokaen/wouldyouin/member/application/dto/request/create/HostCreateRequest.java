package org.ktc2.cokaen.wouldyouin.member.application.dto.request.create;

import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.ktc2.cokaen.wouldyouin.Image.persist.MemberImage;
import org.ktc2.cokaen.wouldyouin.member.persist.Host;

@Getter
@RequiredArgsConstructor
public class HostCreateRequest extends MemberCreateRequestBase {

    protected String phone;
    protected String password;
    protected Long profileImageId;

    public HostCreateRequest(String nickname, String email, String phone, String password, Long profileImageId) {
        super(nickname, email);
        this.phone = phone;
        this.password = password;
        this.profileImageId = profileImageId;
    }

    public Host toEntity(String hashedPassword, MemberImage profileImage) {

        return Host.builder()
            .nickname(this.nickname)
            .profileImage(profileImage)
            .email(this.email)
            .phone(this.phone)
            .hashedPassword(hashedPassword)
            .build();
    }
}
