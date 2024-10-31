package org.ktc2.cokaen.wouldyouin.member.application;

import lombok.RequiredArgsConstructor;
import org.ktc2.cokaen.wouldyouin.member.application.dto.MemberResponse;
import org.ktc2.cokaen.wouldyouin.member.persist.BaseMember;
import org.ktc2.cokaen.wouldyouin.member.persist.BaseMemberRepository;
import org.ktc2.cokaen.wouldyouin.member.persist.MemberType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BaseMemberService {

    private final BaseMemberRepository baseMemberRepository;
    private final DerivedMemberServiceFactory derivedMemberServiceFactory;

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
        return derivedMemberServiceFactory.get(getMemberTypeByIdOrThrow(id)).getMemberResponseById(id);
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
        derivedMemberServiceFactory.get(getMemberTypeByIdOrThrow(id)).deleteById(id);
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
