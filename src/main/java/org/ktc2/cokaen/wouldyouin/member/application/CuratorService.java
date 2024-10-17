package org.ktc2.cokaen.wouldyouin.member.application;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.ktc2.cokaen.wouldyouin._common.api.EntityGettable;
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
public class CuratorService implements MemberServiceCommonBehavior, EntityGettable<Long, Curator> {

    private final CuratorRepository curatorRepository;
    private final MemberRepository memberRepository;
    private final BaseMemberRepository baseMemberRepository;

    @Transactional
    public MemberResponse createCurator(Long normalMemberId) {
        //TODO: 커스텀 예외 필요
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

        Long toDeleteId = member.getId();
        memberRepository.deleteById(toDeleteId);
        baseMemberRepository.deleteById(toDeleteId);

        //삭제 후 플러시를 사용해 즉시 데이터베이스에 반영
        memberRepository.flush();
        baseMemberRepository.flush();

        curatorRepository.save(curator);
        return MemberResponse.from(curator);
    }

    @Transactional
    public MemberResponse updateCurator(Long curatorId, CuratorEditRequest request) {
        Curator curator = getByIdOrThrow(curatorId);
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
        curatorRepository.delete(getByIdOrThrow(id));
    }

    @Override
    @Transactional(readOnly = true)
    public MemberResponse getMemberResponseById(Long id) {
        return MemberResponse.from(getByIdOrThrow(id));
    }

    @Override
    @Transactional(readOnly = true)
    public Curator getByIdOrThrow(Long id) {
        //TODO: 커스텀 예외 필요
        return curatorRepository.findById(id).orElseThrow(RuntimeException::new);
    }

}
