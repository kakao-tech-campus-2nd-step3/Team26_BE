package org.ktc2.cokaen.wouldyouin.auth.application;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.ktc2.cokaen.wouldyouin.auth.MemberIdentifier;
import org.ktc2.cokaen.wouldyouin.auth.api.dto.LocalLoginRequest;
import org.ktc2.cokaen.wouldyouin.auth.api.dto.LocalSignupRequest;
import org.ktc2.cokaen.wouldyouin.auth.api.dto.SocialLoginRequest;
import org.ktc2.cokaen.wouldyouin.auth.api.dto.SocialTokenResponse;
import org.ktc2.cokaen.wouldyouin.auth.api.dto.TokenResponse;
import org.ktc2.cokaen.wouldyouin.auth.application.oauth.OauthRequestServiceFactory;
import org.ktc2.cokaen.wouldyouin.auth.application.oauth.dto.OauthResourcesResponse;
import org.ktc2.cokaen.wouldyouin.member.api.dto.MemberResponse;
import org.ktc2.cokaen.wouldyouin.member.api.dto.request.MemberAdditionalInfoRequest;
import org.ktc2.cokaen.wouldyouin.member.api.dto.request.create.MemberCreateRequest;
import org.ktc2.cokaen.wouldyouin.member.application.BaseMemberService;
import org.ktc2.cokaen.wouldyouin.member.application.HostService;
import org.ktc2.cokaen.wouldyouin.member.application.MemberService;
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
        MemberResponse response = hostService.createHost(request);
        return TokenResponse.of(createToken(response), response.getMemberId(), response.getMemberType());
    }

    @Transactional(readOnly = true)
    public TokenResponse localLogin(LocalLoginRequest request) {
        MemberResponse response = hostService.getMemberResponseBy(request);
        return TokenResponse.of(createToken(response), response.getMemberId(), response.getMemberType());
    }

    @Transactional
    public SocialTokenResponse socialLogin(SocialLoginRequest request) {
        AccountType accountType = request.accountType();
        String code = request.token();
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
                    .memberId(id.id())
                    .memberType(id.type())
                    .build();
            }
            // 소셜 계정이지만 아직 추가 정보 기입이 되지 않은 경우
            else {
                return SocialTokenResponse.builder()
                    .isWelcomeMember(true)
                    .token(createToken(id))
                    .memberId(id.id())
                    .memberType(id.type())
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
            .memberId(welcomeMemberResponse.getMemberId())
            .memberType(welcomeMemberResponse.getMemberType())
            .build();
    }

    @Transactional
    public TokenResponse acceptAdditionalInfo(Long welcomeMemberId, MemberAdditionalInfoRequest request) {
        MemberResponse response = memberService.updateWelcomeMember(welcomeMemberId, request);
        return TokenResponse.of(createToken(response), response.getMemberId(), response.getMemberType());
    }

    private String createToken(MemberIdentifier identifier) {
        return jwtService.createAccessToken(identifier.id(), identifier.type());
    }

    private String createToken(MemberResponse memberResponse) {
        return jwtService.createAccessToken(memberResponse.getMemberId(), memberResponse.getMemberType());
    }
}
