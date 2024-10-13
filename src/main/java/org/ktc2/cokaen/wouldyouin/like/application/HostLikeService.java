package org.ktc2.cokaen.wouldyouin.like.application;

import java.util.Map;
import java.util.function.Function;
import org.ktc2.cokaen.wouldyouin._common.api.EntityGettable;
import org.ktc2.cokaen.wouldyouin.like.persist.HostLike;
import org.ktc2.cokaen.wouldyouin.like.persist.HostLikeRepository;
import org.ktc2.cokaen.wouldyouin.like.persist.LikeRepository;
import org.ktc2.cokaen.wouldyouin.member.persist.Host;
import org.ktc2.cokaen.wouldyouin.member.persist.LikeableMember;
import org.ktc2.cokaen.wouldyouin.member.persist.Member;
import org.ktc2.cokaen.wouldyouin.member.persist.MemberType;
import org.springframework.stereotype.Service;

@Service
public class HostLikeService extends LikeService<HostLike> {

    private final HostLikeRepository likeRepository;

    public HostLikeService(
        Map<String, EntityGettable<? extends LikeableMember>> likeableMemberGetter,
        EntityGettable<Member> memberGetter,
        HostLikeRepository likeRepository) {
        super(likeableMemberGetter, memberGetter);
        this.likeRepository = likeRepository;
    }

    @Override
    protected LikeRepository<HostLike> getLikeRepository() {
        return likeRepository;
    }

    @Override
    protected Function<HostLike, LikeResponse> getLikeToResponseMapper() {
        return like -> LikeResponse.builder()
            .memberId(like.getLikeableMember().getId())
            .nickname(like.getLikeableMember().getNickname())
            .intro(like.getLikeableMember().getIntro())
            .hashtags(like.getLikeableMember().getHashTagList())
            .profileImageUrl(like.getLikeableMember().getProfileImageUrl())
            .build();
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
