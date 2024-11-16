package org.ktc2.cokaen.wouldyouin.auth.api;

import lombok.RequiredArgsConstructor;
import org.ktc2.cokaen.wouldyouin._common.api.ApiResponse;
import org.ktc2.cokaen.wouldyouin._common.api.ApiResponseBody;
import org.ktc2.cokaen.wouldyouin.auth.Authorize;
import org.ktc2.cokaen.wouldyouin.auth.MemberIdentifier;
import org.ktc2.cokaen.wouldyouin.auth.api.dto.LocalLoginRequest;
import org.ktc2.cokaen.wouldyouin.auth.api.dto.LocalSignupRequest;
import org.ktc2.cokaen.wouldyouin.auth.api.dto.SocialLoginRequest;
import org.ktc2.cokaen.wouldyouin.auth.api.dto.SocialTokenResponse;
import org.ktc2.cokaen.wouldyouin.auth.api.dto.TokenResponse;
import org.ktc2.cokaen.wouldyouin.auth.application.AuthService;
import org.ktc2.cokaen.wouldyouin.member.api.dto.request.MemberAdditionalInfoRequest;
import org.ktc2.cokaen.wouldyouin.member.persist.AccountType;
import org.ktc2.cokaen.wouldyouin.member.persist.MemberType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    // 로컬 계정의 회원가입 처리
    @PostMapping("/local/signup")
    public ResponseEntity<ApiResponseBody<TokenResponse>> signupLocal(@RequestBody LocalSignupRequest signupRequest) {
        return ApiResponse.created(authService.localSignup(signupRequest));
    }

    // 로컬 계정의 로그인 처리
    @PostMapping("/local/login")
    public ResponseEntity<ApiResponseBody<TokenResponse>> loginLocal(@RequestBody LocalLoginRequest request) {
        return ApiResponse.ok(authService.localLogin(request));
    }

    // 소셜 로그인
    @PostMapping("/social/login")
    public ResponseEntity<ApiResponseBody<SocialTokenResponse>> processSocialLogin(@RequestBody SocialLoginRequest loginRequest) {
        return ApiResponse.ok(authService.socialLogin(loginRequest));
    }

    // 소셜 로그인 추가정보 입력 API
    @PostMapping("/social/additional-info")
    public ResponseEntity<ApiResponseBody<TokenResponse>> processAdditionalInfo(@Authorize(MemberType.welcome) MemberIdentifier identifier,
        @RequestBody MemberAdditionalInfoRequest request) {
        return ApiResponse.ok(authService.acceptAdditionalInfo(identifier.id(), request));
    }


}
