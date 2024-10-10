package org.ktc2.cokaen.wouldyouin.member.application;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.ktc2.cokaen.wouldyouin.member.application.dto.request.edit.CuratorEditRequest;
import org.ktc2.cokaen.wouldyouin.member.application.dto.MemberResponse;
import org.ktc2.cokaen.wouldyouin.member.persist.BaseMemberRepository;
import org.ktc2.cokaen.wouldyouin.member.persist.Curator;
import org.ktc2.cokaen.wouldyouin.member.persist.CuratorRepository;
import org.ktc2.cokaen.wouldyouin.member.persist.Member;
import org.ktc2.cokaen.wouldyouin.member.persist.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CuratorService implements MemberServiceCommonBehavior {

    private final CuratorRepository curatorRepository;
    private final MemberRepository memberRepository;
    private final BaseMemberRepository baseMemberRepository;

    @Transactional
    public MemberResponse createCurator(Long normalMemberId) {
        //TODO: 커스텀 예외 필요, 오직 normal만 되도록 처리 필요
        Member member = memberRepository.findById(normalMemberId).orElseThrow(RuntimeException::new);

        // 일반 멤버 정보로 큐레이터 생성 후, 기존 일반멤버 및 BaseMember 정보는 데이터베이스에서 제거
        Curator curator = Curator.curatorBuilder()
            .accountType(member.getAccountType())
            .email(member.getEmail())
            .nickname(member.getNickname())
            .phone(member.getPhone())
            .profileImageUrl(member.getProfileImageUrl())
            .area(member.getArea())
            .gender(member.getGender())
            .socialId(member.getSocialId())
            .build();

        curatorRepository.save(curator);
        memberRepository.delete(member);
        baseMemberRepository.delete(member);

        return MemberResponse.from(curator);
    }

    @Transactional
    public MemberResponse updateCurator(Long curatorId, CuratorEditRequest request) {
        Curator curator = getCuratorOrThrow(curatorId);
        Optional.ofNullable(curator.getNickname()).ifPresent(curator::setNickname);
        Optional.ofNullable(curator.getPhone()).ifPresent(curator::setPhone);
        Optional.ofNullable(curator.getProfileImageUrl()).ifPresent(curator::setProfileImageUrl);
        Optional.ofNullable(curator.getArea()).ifPresent(curator::setArea);
        Optional.ofNullable(curator.getIntro()).ifPresent(curator::setIntro);

        return MemberResponse.from(curator);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        curatorRepository.delete(getCuratorOrThrow(id));
    }

    @Override
    @Transactional(readOnly = true)
    public MemberResponse getMemberResponseById(Long id) {
        return MemberResponse.from(getCuratorOrThrow(id));
    }

    @Transactional(readOnly = true)
    protected Curator getCuratorOrThrow(Long id) {
        //TODO: 커스텀 예외 필요
        return curatorRepository.findById(id).orElseThrow(RuntimeException::new);
    }

}
