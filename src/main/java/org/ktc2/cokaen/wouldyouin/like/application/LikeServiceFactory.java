package org.ktc2.cokaen.wouldyouin.like.application;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.ktc2.cokaen.wouldyouin.like.persist.Like;
import org.ktc2.cokaen.wouldyouin.member.application.BaseMemberService;
import org.ktc2.cokaen.wouldyouin.member.persist.LikeableMember;
import org.ktc2.cokaen.wouldyouin.member.persist.MemberType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class LikeServiceFactory {

    private final Map<MemberType, LikeService<? extends Like<? extends LikeableMember>>> map;
    private final BaseMemberService baseMemberService;

    public LikeServiceFactory(
        @Autowired List<LikeService<? extends Like<? extends LikeableMember>>> likeServices,
        @Autowired BaseMemberService baseMemberService) {
        map = likeServices.stream().collect(Collectors.toConcurrentMap(
            LikeService::getTargetLikeableMemberType,
            Function.identity()));
        this.baseMemberService = baseMemberService;
    }

    public LikeService<? extends Like<? extends LikeableMember>> getLikeServiceFrom(MemberType targetLikeableMemberType) {
        if (!LikeableMember.getLikeableMemberTypes().contains(targetLikeableMemberType)) {
            throw new RuntimeException("해당 사용자는 좋아요할 수 없는 사용자 유형입니다.");
        }
        return map.get(targetLikeableMemberType);
    }

    public LikeService<? extends Like<? extends LikeableMember>> getLikeServiceFrom(Long targetMemberId) {
        return getLikeServiceFrom(baseMemberService.getMemberType(targetMemberId));
    }
}
