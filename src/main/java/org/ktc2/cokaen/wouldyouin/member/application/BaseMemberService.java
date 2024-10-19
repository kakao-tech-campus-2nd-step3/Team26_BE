package org.ktc2.cokaen.wouldyouin.member.application;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.ktc2.cokaen.wouldyouin._common.api.EntityGettable;
import org.ktc2.cokaen.wouldyouin.member.application.dto.MemberResponse;
import org.ktc2.cokaen.wouldyouin.member.persist.BaseMember;
import org.ktc2.cokaen.wouldyouin.member.persist.BaseMemberRepository;
import org.ktc2.cokaen.wouldyouin.member.persist.MemberType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BaseMemberService implements EntityGettable<Long, BaseMember> {

    private final BaseMemberRepository baseMemberRepository;
    private final Map<MemberType, MemberServiceCommonBehavior> memberServiceMap;

    public BaseMemberService(@Autowired BaseMemberRepository baseMemberRepository,
        @Autowired List<MemberServiceCommonBehavior> memberServices) {
        this.baseMemberRepository = baseMemberRepository;
        this.memberServiceMap = memberServices.stream().collect(Collectors.toConcurrentMap(
            MemberServiceCommonBehavior::getTargetMemberType,
            Function.identity()));
    }

    @Override
    public BaseMember getByIdOrThrow(Long id) throws RuntimeException {
        // TODO: 커스텀 예외 작성필요
        return baseMemberRepository.findById(id).orElseThrow(RuntimeException::new);
    }

    @Transactional(readOnly = true)
    public MemberType getMemberType(Long id) throws RuntimeException {
        return getByIdOrThrow(id).getMemberType();
    }

    @Transactional(readOnly = true)
    public MemberResponse findById(Long id) {
        return memberServiceMap.get(getMemberTypeByIdOrThrow(id)).getMemberResponseById(id);
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
        memberServiceMap.get(getMemberTypeByIdOrThrow(id)).deleteById(id);
        baseMemberRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    protected MemberType getMemberTypeByIdOrThrow(Long id) {
        MemberType type = getByIdOrThrow(id).getMemberType();

        if (type == MemberType.welcome) {
            return MemberType.normal;
        }
        return type;
    }
}
