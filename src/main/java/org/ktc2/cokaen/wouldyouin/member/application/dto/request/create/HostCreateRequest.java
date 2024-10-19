package org.ktc2.cokaen.wouldyouin.member.application.dto.request.create;

import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.ktc2.cokaen.wouldyouin.Image.persist.MemberImage;
import org.ktc2.cokaen.wouldyouin._common.api.EntityGettable;
import org.ktc2.cokaen.wouldyouin.member.persist.Host;
import org.springframework.security.crypto.password.PasswordEncoder;

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

    public Host toEntity(PasswordEncoder passwordEncoder, EntityGettable<List<Long>, List<MemberImage>> imageIdToMemberImageConverter) {
        return Host.builder()
            .nickname(this.nickname)
            .profileImage(imageIdToMemberImageConverter.getByIdOrThrow(List.of(this.profileImageId)))
            .email(this.email)
            .phone(this.phone)
            .hashedPassword(passwordEncoder.encode(this.password))
            .build();
    }
}
