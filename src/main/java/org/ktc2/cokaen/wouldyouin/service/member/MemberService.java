package org.ktc2.cokaen.wouldyouin.service.member;

import lombok.RequiredArgsConstructor;
import org.ktc2.cokaen.wouldyouin.controller.member.dto.MemberCreateRequest;
import org.ktc2.cokaen.wouldyouin.controller.member.dto.MemberEditRequest;
import org.ktc2.cokaen.wouldyouin.controller.member.dto.MemberResponse;
import org.ktc2.cokaen.wouldyouin.domain.member.Member;
import org.ktc2.cokaen.wouldyouin.repository.member.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    @Transactional
    public MemberResponse createMember(MemberCreateRequest request) {
        return MemberResponse.from(memberRepository.save(request.toEntity()));
    }

    @Transactional
    public MemberResponse updateMember(Long memberId, MemberEditRequest editRequest) {
        Member member = findMemberOrThrow(memberId);
        Optional.ofNullable(editRequest.getNickname()).ifPresent(member::setNickname);
        Optional.ofNullable(editRequest.getArea()).ifPresent(member::setArea);
        Optional.ofNullable(editRequest.getPhoneNumber()).ifPresent(member::setPhone);
        Optional.ofNullable(editRequest.getProfileUrl()).ifPresent(member::setProfileImageUrl);

        return MemberResponse.from(member);
    }

    @Transactional(readOnly = true)
    protected Member findMemberOrThrow(Long id) {
        //TODO: 커스텀 예외 필요
        return memberRepository.findById(id).orElseThrow(RuntimeException::new);
    }
}
