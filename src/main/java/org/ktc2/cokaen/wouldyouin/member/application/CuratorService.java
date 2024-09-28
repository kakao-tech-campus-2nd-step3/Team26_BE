package org.ktc2.cokaen.wouldyouin.member.application;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.ktc2.cokaen.wouldyouin.member.application.dto.CuratorEditRequest;
import org.ktc2.cokaen.wouldyouin.member.application.dto.MemberResponse;
import org.ktc2.cokaen.wouldyouin.member.persist.Curator;
import org.ktc2.cokaen.wouldyouin.member.persist.Member;
import org.ktc2.cokaen.wouldyouin.member.persist.CuratorRepository;
import org.ktc2.cokaen.wouldyouin.member.persist.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CuratorService {

    private final CuratorRepository curatorRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public MemberResponse createCurator(Long normalMemberId) {
        //TODO: 커스텀 예외 필요
        Member member = memberRepository.findById(normalMemberId).orElseThrow(RuntimeException::new);

        // 일반멤버 정보로 큐레이터 생성 후, 기존 일반멤버는 데이터베이스에서 제거
        Curator curator = curatorRepository.save(Curator.curatorBuilder()
            .phone(member.getPhone())
            .area(member.getArea())
            .nickname(member.getNickname())
            .gender(member.getGender())
            .profileImageUrl(member.getProfileImageUrl())
            .build());
        memberRepository.delete(member);

        return MemberResponse.from(curator);
    }

    @Transactional
    public MemberResponse updateCurator(Long curatorId, CuratorEditRequest request) {
        Curator curator = findCuratorOrThrow(curatorId);
        Optional.ofNullable(curator.getNickname()).ifPresent(curator::setNickname);
        Optional.ofNullable(curator.getPhone()).ifPresent(curator::setPhone);
        Optional.ofNullable(curator.getProfileImageUrl()).ifPresent(curator::setProfileImageUrl);
        Optional.ofNullable(curator.getArea()).ifPresent(curator::setArea);
        Optional.ofNullable(curator.getIntro()).ifPresent(curator::setIntro);

        return MemberResponse.from(curator);
    }

    @Transactional(readOnly = true)
    protected Curator findCuratorOrThrow(Long curatorId) {
        //TODO: 커스텀 예외 필요
        return curatorRepository.findById(curatorId).orElseThrow(RuntimeException::new);
    }

}
