package org.ktc2.cokaen.wouldyouin.like.application;

import org.ktc2.cokaen.wouldyouin.like.persist.CuratorLike;
import org.ktc2.cokaen.wouldyouin.like.persist.CuratorLikeRepository;
import org.ktc2.cokaen.wouldyouin.like.persist.LikeRepository;
import org.ktc2.cokaen.wouldyouin.member.application.LikeableMemberGetterFactory;
import org.ktc2.cokaen.wouldyouin.member.application.MemberService;
import org.ktc2.cokaen.wouldyouin.member.persist.Curator;
import org.ktc2.cokaen.wouldyouin.member.persist.LikeableMember;
import org.ktc2.cokaen.wouldyouin.member.persist.Member;
import org.ktc2.cokaen.wouldyouin.member.persist.MemberType;
import org.springframework.stereotype.Service;

@Service
public class CuratorLikeService extends LikeService<CuratorLike> {

    private final CuratorLikeRepository curatorLikeRepository;

    public CuratorLikeService(
        LikeableMemberGetterFactory likeableMemberGetterFactory,
        MemberService memberService, CuratorLikeRepository curatorLikeRepository) {
        super(likeableMemberGetterFactory, memberService);
        this.curatorLikeRepository = curatorLikeRepository;
    }

    @Override
    protected LikeRepository<CuratorLike> getLikeRepository() {
        return curatorLikeRepository;
    }

    @Override
    protected CuratorLike toEntity(Member member, LikeableMember targetLikableMember) {
        return CuratorLike.builder()
            .targetMember((Curator) targetLikableMember)
            .member(member)
            .build();
    }

    @Override
    public MemberType getTargetLikeableMemberType() {
        return MemberType.curator;
    }
}
