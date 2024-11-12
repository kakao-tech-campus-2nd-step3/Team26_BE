package org.ktc2.cokaen.wouldyouin.member.application;

import lombok.RequiredArgsConstructor;
import org.ktc2.cokaen.wouldyouin.Image.application.MemberImageService;
import org.ktc2.cokaen.wouldyouin.Image.persist.MemberImage;
import org.ktc2.cokaen.wouldyouin._common.exception.EntityNotFoundException;
import org.ktc2.cokaen.wouldyouin.member.api.dto.MemberResponse;
import org.ktc2.cokaen.wouldyouin.member.api.dto.request.edit.CuratorEditRequest;
import org.ktc2.cokaen.wouldyouin.member.persist.BaseMemberRepository;
import org.ktc2.cokaen.wouldyouin.member.persist.Curator;
import org.ktc2.cokaen.wouldyouin.member.persist.CuratorRepository;
import org.ktc2.cokaen.wouldyouin.member.persist.Member;
import org.ktc2.cokaen.wouldyouin.member.persist.MemberRepository;
import org.ktc2.cokaen.wouldyouin.member.persist.MemberType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CuratorService implements MemberServiceCommonBehavior, LikeableMemberService<Curator> {

    private final CuratorRepository curatorRepository;
    private final MemberRepository memberRepository;
    private final BaseMemberRepository baseMemberRepository;
    private final MemberImageService memberImageService;

    @Override
    public LikeableMemberService<Curator> getLikeableMemberService() {
        return this;
    }

    @Override
    @Transactional(readOnly = true)
    public MemberResponse getMemberResponseById(Long id) {
        return MemberResponse.from(getByIdOrThrow(id));
    }

    @Override
    public MemberType getTargetMemberType() {
        return MemberType.curator;
    }

    @Transactional(readOnly = true)
    public Curator getByIdOrThrow(Long id) {
        return curatorRepository.findById(id).
            orElseThrow(() -> new EntityNotFoundException("해당하는 큐레이터 정보를 찾을 수 없습니다."));
    }

    @Transactional
    public MemberResponse createCurator(Long normalMemberId) {
        Member member = memberRepository.findById(normalMemberId)
            .orElseThrow(() -> new EntityNotFoundException("해당하는 멤버 정보를 찾을 수 없습니다."));

        // 일반 멤버 정보로 큐레이터 생성 후, 기존 일반멤버 및 BaseMember 정보는 데이터베이스에서 제거
        Curator curator = Curator.curatorBuilder()
            .accountType(member.getAccountType())
            .email(member.getEmail())
            .nickname(member.getNickname())
            .phone(member.getPhone())
            .profileImage(member.getProfileImage())
            .profileImageThumbnailUrl(member.getProfileImageThumbnailUrl())
            .area(member.getArea())
            .gender(member.getGender())
            .socialId(member.getSocialId())
            .build();

        Long toDeleteId = member.getId();
        memberRepository.deleteById(toDeleteId);
        baseMemberRepository.deleteById(toDeleteId);

        //삭제 후 플러시를 사용해 즉시 데이터베이스에 반영
        memberRepository.flush();
        baseMemberRepository.flush();

        curatorRepository.save(curator);
        return MemberResponse.from(curator);
    }

    // TODO : 반대방향 연관관계 설정 setter?
    @Transactional
    public MemberResponse updateCurator(Long curatorId, CuratorEditRequest request) {
        Curator curator = getByIdOrThrow(curatorId);
        MemberImage image = memberImageService.getById(request.getProfileImageId());
        String thumbnailImageUrl = memberImageService.createThumbnail(image.getName());
        curator.updateFrom(request, image, thumbnailImageUrl);
        image.setBaseMember(curator);
        return MemberResponse.from(curator);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        curatorRepository.delete(getByIdOrThrow(id));
    }
}
