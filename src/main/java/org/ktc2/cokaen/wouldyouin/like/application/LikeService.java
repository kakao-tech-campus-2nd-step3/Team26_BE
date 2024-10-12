package org.ktc2.cokaen.wouldyouin.like.application;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import lombok.RequiredArgsConstructor;
import org.ktc2.cokaen.wouldyouin._common.api.EntityGettable;
import org.ktc2.cokaen.wouldyouin.like.persist.Like;
import org.ktc2.cokaen.wouldyouin.like.persist.LikeRepository;
import org.ktc2.cokaen.wouldyouin.member.persist.BaseMember;
import org.ktc2.cokaen.wouldyouin.member.persist.LikeableMember;
import org.ktc2.cokaen.wouldyouin.member.persist.Member;
import org.ktc2.cokaen.wouldyouin.member.persist.MemberType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public abstract class LikeService<LikeType extends Like<? extends LikeableMember>> {

    private final Map<String, EntityGettable<? extends LikeableMember>> likeableMemberGetter;
    private final EntityGettable<BaseMember> baseMemberGetter;
    private final EntityGettable<Member> memberGetter;

    protected abstract LikeRepository<LikeType> getLikeRepository();
    protected abstract Function<LikeType, LikeResponse> getLikeToResponseMapper();
    protected abstract LikeType toEntity(Member member, LikeableMember targetLikableMember);
    public abstract MemberType getTargetLikeableMemberType();

    @Transactional(readOnly = true)
    public List<LikeResponse> getLikes(Long memberId) {
        return getLikeRepository().findAllByMember(memberGetter.getByIdOrThrow(memberId))
            .stream()
            .map(getLikeToResponseMapper())
            .toList();
    }

    @Transactional
    public LikeResponse create(Long memberId, Long targetMemberId, MemberType targetMemberType) {
        LikeableMember targetLikeableMember = getLikeableMemberByIdOrThrow(targetMemberId, targetMemberType);
        Member member = memberGetter.getByIdOrThrow(memberId);
        getLikeRepository().findByMemberAndLikeableMember(member, targetLikeableMember).ifPresent(x -> {
            throw new RuntimeException("이미 좋아요한 사용자입니다.");
        });
        return getLikeToResponseMapper().apply(getLikeRepository().save(toEntity(member, targetLikeableMember)));
    }

    @Transactional
    public void delete(Long memberId, Long targetMemberId, MemberType targetMemberType) {
        LikeableMember targetLikeableMember = getLikeableMemberByIdOrThrow(targetMemberId, targetMemberType);
        Member member = memberGetter.getByIdOrThrow(memberId);
        LikeType like = getLikeRepository().findByMemberAndLikeableMember(member, targetLikeableMember)
            .orElseThrow(() -> new RuntimeException("해당 사용자를 좋아요하지 않았습니다."));
        getLikeRepository().delete(like);
    }

    @Transactional(readOnly = true)
    protected LikeableMember getLikeableMemberByIdOrThrow(Long likeableMemberId, MemberType targetMemberType) {
        return likeableMemberGetter.get(targetMemberType.getServiceName()).getByIdOrThrow(likeableMemberId);
    }
}
