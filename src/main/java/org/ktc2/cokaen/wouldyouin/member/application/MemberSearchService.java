package org.ktc2.cokaen.wouldyouin.member.application;

import lombok.RequiredArgsConstructor;
import org.ktc2.cokaen.wouldyouin.member.application.dto.MemberResponse;
import org.ktc2.cokaen.wouldyouin.member.persist.MemberType;
import org.ktc2.cokaen.wouldyouin.member.persist.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberSearchService {

    private final MemberRepository memberRepository;
    private final HostService hostService;
    private final CuratorService curatorService;

    @Transactional(readOnly = true)
    public MemberResponse findByIdAndMemberType(Long id, MemberType memberType) {
        //TODO: 멤버 타입에 따라 다른 엔티티가 검색되게 처리 필요
        return MemberResponse.builder().build();
        //return MemberResponse.from(findMemberOrThrow(id));
    }

}
