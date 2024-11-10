package org.ktc2.cokaen.wouldyouin.auth.application;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.ktc2.cokaen.wouldyouin.auth.MemberIdentifier;
import org.ktc2.cokaen.wouldyouin.auth.api.dto.LocalLoginRequest;
import org.ktc2.cokaen.wouldyouin.auth.api.dto.LocalSignupRequest;
import org.ktc2.cokaen.wouldyouin.auth.api.dto.SocialTokenResponse;
import org.ktc2.cokaen.wouldyouin.auth.api.dto.TokenResponse;
import org.ktc2.cokaen.wouldyouin.auth.application.oauth.OauthRequestServiceFactory;
import org.ktc2.cokaen.wouldyouin.auth.application.oauth.dto.OauthResourcesResponse;
import org.ktc2.cokaen.wouldyouin.member.application.BaseMemberService;
import org.ktc2.cokaen.wouldyouin.member.application.HostService;
import org.ktc2.cokaen.wouldyouin.member.application.MemberService;
import org.ktc2.cokaen.wouldyouin.member.api.dto.MemberResponse;
import org.ktc2.cokaen.wouldyouin.member.api.dto.request.MemberAdditionalInfoRequest;
import org.ktc2.cokaen.wouldyouin.member.api.dto.request.create.MemberCreateRequest;
import org.ktc2.cokaen.wouldyouin.member.persist.AccountType;
import org.ktc2.cokaen.wouldyouin.member.persist.MemberType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final JwtService jwtService;
    private final BaseMemberService baseMemberService;
    private final MemberService memberService;
    private final HostService hostService;
    private final OauthRequestServiceFactory oauthRequestServiceFactory;

    @Transactional
    public TokenResponse localSignup(LocalSignupRequest request) {
        baseMemberService.checkUniqueEmailOrThrow(request.getEmail());
        return TokenResponse.from(createToken(hostService.createHost(request)));
    }

    @Transactional(readOnly = true)
    public TokenResponse localLogin(LocalLoginRequest request) {
        return TokenResponse.from(createToken(hostService.getMemberResponseBy(request)));
    }

    @Transactional
    public SocialTokenResponse socialLogin(AccountType accountType, String code) {
        OauthResourcesResponse resources = oauthRequestServiceFactory.getServiceFrom(accountType).getOauthMemberResources(code);
        Optional<MemberIdentifier> identifier = memberService.getMemberIdentifierBySocialId(resources.getSocialId());

        baseMemberService.checkUniqueEmailOrThrow(resources.getEmail());

        if (identifier.isPresent()) {
            MemberIdentifier id = identifier.get();
            // 소셜 계정이고 추가 정보도 기입된 경우
            if (id.type() != MemberType.welcome) {
                return SocialTokenResponse.builder()
                    .isWelcomeMember(false)
                    .token(createToken(id))
                    .build();
            }
            // 소셜 계정이지만 아직 추가 정보 기입이 되지 않은 경우
            else {
                return SocialTokenResponse.builder()
                    .isWelcomeMember(true)
                    .token(createToken(id))
                    .build();
            }
        }

        // 소셜 계정의 회원가입 처리
        MemberResponse welcomeMemberResponse = memberService.createMember(MemberCreateRequest.builder()
            .nickname(resources.getNickname())
            .email(resources.getEmail())
            .socialId(resources.getSocialId())
            .accountType(accountType)
            .profileImageUrl(resources.getProfileImageUrl())
            .build());

        return SocialTokenResponse.builder()
            .isWelcomeMember(true)
            .token(createToken(welcomeMemberResponse))
            .build();
    }

    @Transactional
    public TokenResponse acceptAdditionalInfo(Long welcomeMemberId, MemberAdditionalInfoRequest request) {
        return TokenResponse.from(createToken(memberService.updateWelcomeMember(welcomeMemberId, request)));
    }

    private String createToken(MemberIdentifier identifier) {
        return jwtService.createAccessToken(identifier.id(), identifier.type());
    }

    private String createToken(MemberResponse memberResponse) {
        return jwtService.createAccessToken(memberResponse.getMemberId(), memberResponse.getMemberType());
    }
}
