package org.ktc2.cokaen.wouldyouin.auth.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ktc2.cokaen.wouldyouin._common.api.ApiResponse;
import org.ktc2.cokaen.wouldyouin._common.api.ApiResponseBody;
import org.ktc2.cokaen.wouldyouin.auth.Authorize;
import org.ktc2.cokaen.wouldyouin.auth.MemberIdentifier;
import org.ktc2.cokaen.wouldyouin.auth.application.AuthService;
import org.ktc2.cokaen.wouldyouin.auth.application.dto.LocalLoginRequest;
import org.ktc2.cokaen.wouldyouin.auth.application.dto.LocalSignupRequest;
import org.ktc2.cokaen.wouldyouin.auth.application.dto.SocialTokenResponse;
import org.ktc2.cokaen.wouldyouin.auth.application.dto.TokenResponse;
import org.ktc2.cokaen.wouldyouin.member.application.dto.request.MemberAdditionalInfoRequest;
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

@Slf4j
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

    // 소셜 로그인 redirect 처리 (회원가입 및 로그인)
    @GetMapping("/social/redirect/{accountType}")
    public ResponseEntity<ApiResponseBody<SocialTokenResponse>> processRedirect(@PathVariable("accountType") AccountType accountType, @RequestParam("code") String code) {
        return ApiResponse.ok(authService.socialLogin(accountType, code));
    }

    // 소셜 로그인 추가정보 입력 API
    @PostMapping("/social/additional-info")
    public ResponseEntity<ApiResponseBody<TokenResponse>> processAdditionalInfo(@Authorize(MemberType.welcome) MemberIdentifier identifier,
        @RequestBody MemberAdditionalInfoRequest request) {
        return ApiResponse.ok(authService.acceptAdditionalInfo(identifier.id(), request));
    }


}
