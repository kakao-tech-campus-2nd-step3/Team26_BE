package org.ktc2.cokaen.wouldyouin.member.application;

import lombok.RequiredArgsConstructor;
import org.ktc2.cokaen.wouldyouin._common.api.EntityGettable;
import org.ktc2.cokaen.wouldyouin.auth.MemberIdentifier;
import org.ktc2.cokaen.wouldyouin.member.application.dto.request.MemberAdditionalInfoRequest;
import org.ktc2.cokaen.wouldyouin.member.application.dto.request.create.MemberCreateRequest;
import org.ktc2.cokaen.wouldyouin.member.application.dto.request.edit.MemberEditRequest;
import org.ktc2.cokaen.wouldyouin.member.application.dto.MemberResponse;
import org.ktc2.cokaen.wouldyouin.member.persist.Member;
import org.ktc2.cokaen.wouldyouin.member.persist.MemberRepository;
import org.ktc2.cokaen.wouldyouin.member.persist.MemberType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService implements MemberServiceCommonBehavior, EntityGettable<Long, Member> {

    private final MemberRepository memberRepository;

    @Transactional
    public MemberResponse createMember(MemberCreateRequest request) {
        return MemberResponse.from(memberRepository.save(request.toEntity()));
    }

    @Transactional
    public MemberResponse updateMember(Long memberId, MemberEditRequest editRequest) {
        Member member = getByIdOrThrow(memberId);
        Optional.ofNullable(editRequest.getNickname()).ifPresent(member::setNickname);
        Optional.ofNullable(editRequest.getArea()).ifPresent(member::setArea);
        Optional.ofNullable(editRequest.getPhoneNumber()).ifPresent(member::setPhone);
        Optional.ofNullable(editRequest.getProfileUrl()).ifPresent(member::setProfileImageUrl);

        return MemberResponse.from(member);
    }

    @Transactional
    public MemberResponse updateWelcomeMember(Long welcomeMemberId, MemberAdditionalInfoRequest additionalInfoRequest) {
        Member member = getByIdOrThrow(welcomeMemberId);
        if (member.getMemberType() != MemberType.welcome) {
            // TODO: 커스텀 예외 필요
            throw new RuntimeException("Welcome Member가 아닙니다.");
        }
        member.updateFrom(additionalInfoRequest);
        return MemberResponse.from(member);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        memberRepository.delete(getByIdOrThrow(id));
    }

    @Override
    @Transactional(readOnly = true)
    public MemberResponse getMemberResponseById(Long id) {
        return MemberResponse.from(getByIdOrThrow(id));
    }

    @Override
    @Transactional(readOnly = true)
    public Member getByIdOrThrow(Long id) {
        //TODO: 커스텀 예외 필요
        return memberRepository.findById(id).orElseThrow(RuntimeException::new);
    }

    @Transactional(readOnly = true)
    public Optional<MemberIdentifier> getMemberIdentifierBySocialId(String socialId) {
        return memberRepository.findBySocialId(socialId).map(m -> new MemberIdentifier(m.getId(), m.getMemberType()));
    }
}