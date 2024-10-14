package org.ktc2.cokaen.wouldyouin.auth.application;

import lombok.RequiredArgsConstructor;
import org.ktc2.cokaen.wouldyouin.auth.application.dto.LocalLoginRequest;
import org.ktc2.cokaen.wouldyouin.auth.application.dto.LocalSignupRequest;
import org.ktc2.cokaen.wouldyouin.auth.application.dto.OauthLoginRequest;
import org.ktc2.cokaen.wouldyouin.auth.application.dto.OauthSignupRequest;
import org.ktc2.cokaen.wouldyouin.auth.application.dto.TokenResponse;
import org.ktc2.cokaen.wouldyouin.member.application.BaseMemberService;
import org.ktc2.cokaen.wouldyouin.member.application.HostService;
import org.ktc2.cokaen.wouldyouin.member.application.MemberService;
import org.ktc2.cokaen.wouldyouin.member.application.dto.request.create.MemberCreateRequest;
import org.ktc2.cokaen.wouldyouin.member.persist.AccountType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final JwtService jwtService;
    private final BaseMemberService baseMemberService;
    private final MemberService memberService;
    private final HostService hostService;

    @Transactional
    public TokenResponse localSignup(LocalSignupRequest request) {
        baseMemberService.checkUniqueEmailOrThrow(request.getEmail());
        return TokenResponse.from(hostService.createHost(request), jwtService);
    }

    @Transactional
    public TokenResponse oauthSignup(AccountType accountType, OauthSignupRequest oauthSignupRequest) {
        MemberCreateRequest request = oauthSignupRequest.toMemberCreateRequest(accountType);
        baseMemberService.checkUniqueEmailOrThrow(request.getEmail());
        return TokenResponse.from(memberService.createMember(request), jwtService);
    }

    @Transactional(readOnly = true)
    public TokenResponse localLogin(LocalLoginRequest request) {
        return TokenResponse.from(hostService.getMemberResponseBy(request), jwtService);
    }

    @Transactional(readOnly = true)
    public TokenResponse oauthLogin(AccountType accountType, OauthLoginRequest request) {
        // TODO: 구현 필요
        return TokenResponse.builder().build();
    }
}
