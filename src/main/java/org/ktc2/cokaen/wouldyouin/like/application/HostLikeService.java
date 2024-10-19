package org.ktc2.cokaen.wouldyouin.like.application;

import org.ktc2.cokaen.wouldyouin._common.api.EntityGettable;
import org.ktc2.cokaen.wouldyouin.like.persist.HostLike;
import org.ktc2.cokaen.wouldyouin.like.persist.HostLikeRepository;
import org.ktc2.cokaen.wouldyouin.like.persist.LikeRepository;
import org.ktc2.cokaen.wouldyouin.member.application.LikeableMemberGetterFactory;
import org.ktc2.cokaen.wouldyouin.member.persist.Host;
import org.ktc2.cokaen.wouldyouin.member.persist.LikeableMember;
import org.ktc2.cokaen.wouldyouin.member.persist.Member;
import org.ktc2.cokaen.wouldyouin.member.persist.MemberType;
import org.springframework.stereotype.Service;

@Service
public class HostLikeService extends LikeService<HostLike> {

    private final HostLikeRepository hostLikeRepository;

    public HostLikeService(
        LikeableMemberGetterFactory likeableMemberGetterFactory,
        EntityGettable<Long, Member> memberGetter, HostLikeRepository hostLikeRepository) {
        super(likeableMemberGetterFactory, memberGetter);
        this.hostLikeRepository = hostLikeRepository;
    }

    @Override
    protected LikeRepository<HostLike> getLikeRepository() {
        return hostLikeRepository;
    }

    @Override
    protected HostLike toEntity(Member member, LikeableMember targetLikableMember) {
        return HostLike.builder()
            .targetMember((Host)targetLikableMember)
            .member(member)
            .build();
    }

    @Override
    public MemberType getTargetLikeableMemberType() {
        return MemberType.host;
    }
}
