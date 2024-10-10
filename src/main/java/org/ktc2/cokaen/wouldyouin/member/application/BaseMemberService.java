package org.ktc2.cokaen.wouldyouin.member.application;

import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.ktc2.cokaen.wouldyouin.member.application.dto.MemberResponse;
import org.ktc2.cokaen.wouldyouin.member.persist.BaseMemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BaseMemberService {

    private final BaseMemberRepository baseMemberRepository;
    private final Map<String, MemberServiceCommonBehavior> memberServiceMap;

    @Transactional(readOnly = true)
    public MemberResponse findById(Long id) {
        return memberServiceMap.get(getServiceNameByIdOrThrow(id)).getMemberResponseById(id);
    }

    @Transactional
    public void deleteById(Long id) {
        memberServiceMap.get(getServiceNameByIdOrThrow(id)).deleteById(id);
        baseMemberRepository.deleteById(id);
    }

    private String getServiceNameByIdOrThrow(Long id) {
        // TODO: 커스텀 예외 작성필요
        return baseMemberRepository.findById(id)
            .orElseThrow(RuntimeException::new)
            .getMemberType()
            .getServiceName();
    }
}
