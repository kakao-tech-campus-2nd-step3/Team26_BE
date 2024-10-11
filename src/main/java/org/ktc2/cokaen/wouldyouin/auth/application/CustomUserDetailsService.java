package org.ktc2.cokaen.wouldyouin.auth.application;

import lombok.RequiredArgsConstructor;
import org.ktc2.cokaen.wouldyouin.auth.MemberIdentifier;
import org.ktc2.cokaen.wouldyouin.auth.persist.CustomUserDetails;
import org.ktc2.cokaen.wouldyouin.member.persist.BaseMember;
import org.ktc2.cokaen.wouldyouin.member.persist.BaseMemberRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final BaseMemberRepository baseMemberRepository;

    @Override
    public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {
        // TODO: 커스텀 예외 필요
        BaseMember member = baseMemberRepository.findById(Long.parseLong(id)).orElseThrow(RuntimeException::new);
        return new CustomUserDetails(new MemberIdentifier(member.getId(), member.getMemberType()));
    }
}
