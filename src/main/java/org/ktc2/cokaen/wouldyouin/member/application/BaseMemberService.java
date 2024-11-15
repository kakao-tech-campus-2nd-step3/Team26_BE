package org.ktc2.cokaen.wouldyouin.member.application;

import lombok.RequiredArgsConstructor;
import org.ktc2.cokaen.wouldyouin._common.exception.EntityNotFoundException;
import org.ktc2.cokaen.wouldyouin.member.api.dto.MemberResponse;
import org.ktc2.cokaen.wouldyouin.member.exception.EmailAlreadyExistsException;
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

    public BaseMember getByIdOrThrow(Long id) throws EntityNotFoundException {
        return baseMemberRepository.findById(id).orElseThrow(() ->
            new EntityNotFoundException("아이디에 해당하는 사용자를 찾을 수 없습니다.")
        );
    }

    @Transactional(readOnly = true)
    public MemberType getMemberType(Long id) throws EntityNotFoundException {
        return getByIdOrThrow(id).getMemberType();
    }

    @Transactional(readOnly = true)
    public MemberResponse findById(Long id) {
        return derivedMemberServiceFactory.get(getMemberTypeByIdOrThrow(id)).getMemberResponseById(id);
    }

    @Transactional(readOnly = true)
    public void checkUniqueEmailOrThrow(String email) {
        baseMemberRepository.findByEmail(email).ifPresent(p -> {
            throw new EmailAlreadyExistsException("이미 사용중인 이메일입니다.");
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
