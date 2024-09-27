package org.ktc2.cokaen.wouldyouin.global.util;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.ktc2.cokaen.wouldyouin.domain.MemberType;
import org.ktc2.cokaen.wouldyouin.domain.member.Member;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
//TODO: 토큰처리 로직추가
public class JwtManager {

    public String getTokenFromMember(Member member) {
        return member.getId().toString();
    }

    public UUID getMemberFromToken(MemberType memberType, String token) {
        return UUID.fromString(token);
    }
}
