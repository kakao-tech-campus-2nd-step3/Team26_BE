package org.ktc2.cokaen.wouldyouin.like.application;

import java.util.Map;
import org.ktc2.cokaen.wouldyouin._common.api.EntityGettable;
import org.ktc2.cokaen.wouldyouin.like.persist.CuratorLike;
import org.ktc2.cokaen.wouldyouin.like.persist.CuratorLikeRepository;
import org.ktc2.cokaen.wouldyouin.like.persist.LikeRepository;
import org.ktc2.cokaen.wouldyouin.member.persist.Curator;
import org.ktc2.cokaen.wouldyouin.member.persist.LikeableMember;
import org.ktc2.cokaen.wouldyouin.member.persist.Member;
import org.ktc2.cokaen.wouldyouin.member.persist.MemberType;
import org.springframework.stereotype.Service;

@Service
public class CuratorLikeService extends LikeService<CuratorLike> {

    private final CuratorLikeRepository curatorLikeRepository;

    public CuratorLikeService(
        Map<String, EntityGettable<Long, ? extends LikeableMember>> likeableMemberGetter, EntityGettable<Long, Member> memberGetter,
        CuratorLikeRepository curatorLikeRepository) {
        super(likeableMemberGetter, memberGetter);
        this.curatorLikeRepository = curatorLikeRepository;
    }

    @Override
    protected LikeRepository<CuratorLike> getLikeRepository() {
        return curatorLikeRepository;
    }

    @Override
    protected CuratorLike toEntity(Member member, LikeableMember targetLikableMember) {
        return CuratorLike.builder()
            .targetMember((Curator)targetLikableMember)
            .member(member)
            .build();
    }

    @Override
    public MemberType getTargetLikeableMemberType() {
        return MemberType.curator;
    }
}
