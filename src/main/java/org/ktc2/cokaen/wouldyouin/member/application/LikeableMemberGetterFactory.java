package org.ktc2.cokaen.wouldyouin.member.application;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.ktc2.cokaen.wouldyouin._common.api.EntityGettable;
import org.ktc2.cokaen.wouldyouin.member.persist.LikeableMember;
import org.ktc2.cokaen.wouldyouin.member.persist.MemberType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class LikeableMemberGetterFactory {

    private final Map<MemberType, EntityGettable<Long, ? extends LikeableMember>> map;

    public LikeableMemberGetterFactory(@Autowired List<LikeableMemberService<? extends LikeableMember>> likeableMemberServices) {
        map = likeableMemberServices.stream()
            .collect(Collectors.toConcurrentMap(
                LikeableMemberService::getTargetMemberType,
                LikeableMemberService::getLikeableMemberGetter));
    }

    public EntityGettable<Long, ? extends LikeableMember> get(MemberType memberType) {
        return map.get(memberType);
    }
}
