package org.ktc2.cokaen.wouldyouin.like.application;

import lombok.RequiredArgsConstructor;
import org.ktc2.cokaen.wouldyouin.like.application.dto.LikeResponse;
import org.ktc2.cokaen.wouldyouin.like.application.dto.LikeToggleResponse;
import org.ktc2.cokaen.wouldyouin.like.persist.Like;
import org.ktc2.cokaen.wouldyouin.like.persist.LikeRepository;
import org.ktc2.cokaen.wouldyouin.member.application.LikeableMemberGetterFactory;
import org.ktc2.cokaen.wouldyouin.member.application.MemberService;
import org.ktc2.cokaen.wouldyouin.member.persist.LikeableMember;
import org.ktc2.cokaen.wouldyouin.member.persist.Member;
import org.ktc2.cokaen.wouldyouin.member.persist.MemberType;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public abstract class LikeService<LikeType extends Like<? extends LikeableMember>> {

    private final LikeableMemberGetterFactory likeableMemberGetterFactory;
    private final MemberService memberService;

    protected abstract LikeRepository<LikeType> getLikeRepository();

    protected abstract LikeType toEntity(Member member, LikeableMember targetLikableMember);

    public abstract MemberType getTargetLikeableMemberType();

    @Transactional(readOnly = true)
    public Slice<LikeResponse> getLikes(Long memberId, Pageable pageable, Long lastId) {
        return getLikeRepository().findAllByMember(
                memberService.getByIdOrThrow(memberId), lastId, pageable)
            .map(like -> LikeResponse.from(like.getLikeableMember()));
    }

    @Transactional
    public LikeToggleResponse toggleLike(Long memberId, Long targetMemberId) {
        Member member = memberService.getByIdOrThrow(memberId);
        LikeableMember targetLikeableMember = getLikeableMemberByIdOrThrow(targetMemberId);
        return getLikeRepository().findByMemberAndLikeableMember(member, targetLikeableMember)
            .map(like -> {
                targetLikeableMember.decreaseLikes();
                getLikeRepository().delete(like);
                return LikeToggleResponse.from(false);
            })
            .orElseGet(() -> {
                targetLikeableMember.increaseLikes();
                getLikeRepository().save(toEntity(member, targetLikeableMember));
                return LikeToggleResponse.from(true);
            });
    }

    @Transactional(readOnly = true)
    protected LikeableMember getLikeableMemberByIdOrThrow(Long likeableMemberId) {
        return likeableMemberGetterFactory.get(getTargetLikeableMemberType())
            .getByIdOrThrow(likeableMemberId);
    }
}
