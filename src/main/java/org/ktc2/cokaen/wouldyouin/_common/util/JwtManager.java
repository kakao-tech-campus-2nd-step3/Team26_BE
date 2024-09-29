package org.ktc2.cokaen.wouldyouin._common.util;

import lombok.RequiredArgsConstructor;
import org.ktc2.cokaen.wouldyouin.security.MemberIdentifier;
import org.ktc2.cokaen.wouldyouin.member.persist.MemberType;
import org.ktc2.cokaen.wouldyouin.member.persist.Member;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
//TODO: 토큰처리 로직추가
public class JwtManager {

    public String getTokenFromMember(Member member) {
        return member.getId().toString();
    }

    public MemberIdentifier getMemberIdentifierFrom(String token) {
        //TODO: 구현 필요
        return new MemberIdentifier(1L, MemberType.normal);
    }
}
