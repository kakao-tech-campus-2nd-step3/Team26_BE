package org.ktc2.cokaen.wouldyouin.member.application;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.ktc2.cokaen.wouldyouin.image.application.MemberImageService;
import org.ktc2.cokaen.wouldyouin.image.persist.MemberImage;
import org.ktc2.cokaen.wouldyouin.auth.MemberIdentifier;
import org.ktc2.cokaen.wouldyouin.member.api.dto.MemberResponse;
import org.ktc2.cokaen.wouldyouin.member.api.dto.request.MemberAdditionalInfoRequest;
import org.ktc2.cokaen.wouldyouin.member.api.dto.request.create.MemberCreateRequest;
import org.ktc2.cokaen.wouldyouin.member.api.dto.request.edit.MemberEditRequest;
import org.ktc2.cokaen.wouldyouin.member.persist.Member;
import org.ktc2.cokaen.wouldyouin.member.persist.MemberRepository;
import org.ktc2.cokaen.wouldyouin.member.persist.MemberType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberService implements MemberServiceCommonBehavior {

    private final MemberRepository memberRepository;
    private final MemberImageService memberImageService;

    @Transactional
    public MemberResponse createMember(MemberCreateRequest request) {
        MemberImage profileImage = memberImageService.convert(request.getProfileImageUrl());
        String thumbnailImageUrl = memberImageService.createThumbnail(profileImage.getName());
        Member member = memberRepository.save(request.toEntity(profileImage, thumbnailImageUrl));
        memberImageService.setBaseMember(profileImage, member);
        return MemberResponse.from(member, memberImageService.getImageUrl(profileImage));
    }

    // TODO : 리팩토링 꼭 할 것, 멤버 전체에 대해 연관관계 설정할 것
    @Transactional
    public MemberResponse updateMember(Long memberId, MemberEditRequest editRequest) {
        Member member = getByIdOrThrow(memberId);
        Optional.ofNullable(editRequest.getNickname()).ifPresent(member::setNickname);
        Optional.ofNullable(editRequest.getArea()).ifPresent(member::setArea);
        Optional.ofNullable(editRequest.getPhoneNumber()).ifPresent(member::setPhone);
        Optional.ofNullable(editRequest.getProfileImageId())
            .map(memberImageService::getById)
            .ifPresent((image) -> {
                member.setProfileImage(image);
                String url = memberImageService.createThumbnail(memberImageService.createThumbnail(image.getName()));
                member.setProfileImageThumbnailUrl(url);
            });
        return MemberResponse.from(member, memberImageService.getImageUrl(member.getProfileImage()));
    }

    @Transactional
    public MemberResponse updateWelcomeMember(Long welcomeMemberId, MemberAdditionalInfoRequest additionalInfoRequest) {
        Member member = getByIdOrThrow(welcomeMemberId);
        // TODO : validate
        if (member.getMemberType() != MemberType.welcome) {
            // TODO: 커스텀 예외 필요
            throw new RuntimeException("Welcome Member가 아닙니다.");
        }
        member.updateFrom(additionalInfoRequest);
        return MemberResponse.from(member, memberImageService.getImageUrl(member.getProfileImage()));
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        memberRepository.delete(getByIdOrThrow(id));
    }

    @Override
    @Transactional(readOnly = true)
    public MemberResponse getMemberResponseById(Long id) {
        Member member = getByIdOrThrow(id);
        return MemberResponse.from(member, memberImageService.getImageUrl(member.getProfileImage()));
    }

    @Transactional(readOnly = true)
    public Member getByIdOrThrow(Long id) {
        //TODO: 커스텀 예외 필요
        return memberRepository.findById(id).orElseThrow(RuntimeException::new);
    }

    @Transactional(readOnly = true)
    public Optional<MemberIdentifier> getMemberIdentifierBySocialId(String socialId) {
        return memberRepository.findBySocialId(socialId).map(m -> new MemberIdentifier(m.getId(), m.getMemberType()));
    }

    @Override
    public MemberType getTargetMemberType() {
        return MemberType.normal;
    }
}