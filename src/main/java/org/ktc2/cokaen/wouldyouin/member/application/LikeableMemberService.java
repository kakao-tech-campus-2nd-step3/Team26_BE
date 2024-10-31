package org.ktc2.cokaen.wouldyouin.member.application;

import org.ktc2.cokaen.wouldyouin.member.persist.LikeableMember;
import org.ktc2.cokaen.wouldyouin.member.persist.MemberType;

public interface LikeableMemberService<T extends LikeableMember> {

    MemberType getTargetMemberType();

    LikeableMemberService<T> getLikeableMemberService();

    T getByIdOrThrow(Long id);
}
