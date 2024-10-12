package org.ktc2.cokaen.wouldyouin.member.persist;

import java.util.List;

public interface LikeableMember {

    static List<MemberType> getLikeableMemberTypes() {
        return List.of(MemberType.host, MemberType.curator);
    }
}
