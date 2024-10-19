package org.ktc2.cokaen.wouldyouin.member.application;

import org.ktc2.cokaen.wouldyouin._common.api.EntityGettable;
import org.ktc2.cokaen.wouldyouin.member.persist.LikeableMember;
import org.ktc2.cokaen.wouldyouin.member.persist.MemberType;

public interface LikeableMemberService<T extends LikeableMember> {

    MemberType getTargetMemberType();

    EntityGettable<Long, T> getLikeableMemberGetter();

}
