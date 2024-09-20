package org.ktc2.cokaen.wouldyouin.service;

import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.ktc2.cokaen.wouldyouin.controller.member.MemberCreateRequest;
import org.ktc2.cokaen.wouldyouin.controller.member.MemberEditRequest;
import org.ktc2.cokaen.wouldyouin.controller.member.MemberResponse;
import org.ktc2.cokaen.wouldyouin.domain.Member;
import org.ktc2.cokaen.wouldyouin.repository.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    @Transactional(readOnly = true)
    public MemberResponse getById(UUID id) {
        return MemberResponse.from(findMemberOrThrow(id));
    }

    @Transactional
    public MemberResponse createMember(MemberCreateRequest request) {
        return MemberResponse.from(memberRepository.save(request.toEntity()));
    }

    @Transactional
    public MemberResponse modifyMember(UUID memberId, MemberEditRequest editRequest) {
        Member member = findMemberOrThrow(memberId);
        Optional.ofNullable(editRequest.getNickname()).ifPresent(member::setNickname);
        Optional.ofNullable(editRequest.getArea()).ifPresent(member::setArea);
        Optional.ofNullable(editRequest.getPhoneNumber()).ifPresent(member::setPhone);
        Optional.ofNullable(editRequest.getProfileUrl()).ifPresent(member::setProfileImageUrl);

        //TODO: 일반사용자가 아닌 경우 처리 필요
        Optional.ofNullable(editRequest.getIntro()).ifPresent(m -> {});
        Optional.ofNullable(editRequest.getHashtag()).ifPresent(m -> {});
        Optional.ofNullable(editRequest.getCuratorProfileUrl()).ifPresent(m -> {});

        return MemberResponse.from(member);
    }

    @Transactional
    public void deleteMemberById(UUID memberId) {
        memberRepository.delete(findMemberOrThrow(memberId));
    }

    @Transactional(readOnly = true)
    protected Member findMemberOrThrow(UUID id) {
        return memberRepository.findById(id).orElseThrow(RuntimeException::new);
    }
}
