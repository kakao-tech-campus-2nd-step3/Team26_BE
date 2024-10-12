package org.ktc2.cokaen.wouldyouin.member.application;

import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.ktc2.cokaen.wouldyouin._common.api.EntityGettable;
import org.ktc2.cokaen.wouldyouin.member.application.dto.MemberResponse;
import org.ktc2.cokaen.wouldyouin.member.persist.BaseMember;
import org.ktc2.cokaen.wouldyouin.member.persist.BaseMemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BaseMemberService implements EntityGettable<BaseMember> {

    private final BaseMemberRepository baseMemberRepository;
    private final Map<String, MemberServiceCommonBehavior> memberServiceMap;

    @Override
    public BaseMember getByIdOrThrow(Long id) throws RuntimeException {
        // TODO: 커스텀 예외 작성필요
        return baseMemberRepository.findById(id).orElseThrow(RuntimeException::new);
    }

    @Transactional(readOnly = true)
    public MemberResponse findById(Long id) {
        return memberServiceMap.get(getServiceNameByIdOrThrow(id)).getMemberResponseById(id);
    }

    @Transactional(readOnly = true)
    public void checkUniqueEmailOrThrow(String email) {
        // TODO: 커스텀 예외 추가필요
        baseMemberRepository.findByEmail(email).ifPresent(p -> {
            throw new RuntimeException("Email already exists");
        });
    }

    @Transactional
    public void deleteById(Long id) {
        memberServiceMap.get(getServiceNameByIdOrThrow(id)).deleteById(id);
        baseMemberRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    protected String getServiceNameByIdOrThrow(Long id) {
        return getByIdOrThrow(id)
            .getMemberType()
            .getServiceName();
    }
}
