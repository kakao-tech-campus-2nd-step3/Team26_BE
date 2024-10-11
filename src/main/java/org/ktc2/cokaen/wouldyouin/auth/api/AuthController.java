package org.ktc2.cokaen.wouldyouin.auth.api;

import lombok.RequiredArgsConstructor;
import org.ktc2.cokaen.wouldyouin._common.api.ApiResponse;
import org.ktc2.cokaen.wouldyouin._common.api.ApiResponseBody;
import org.ktc2.cokaen.wouldyouin.auth.application.AuthService;
import org.ktc2.cokaen.wouldyouin.auth.application.dto.LocalLoginRequest;
import org.ktc2.cokaen.wouldyouin.auth.application.dto.LocalSignupRequest;
import org.ktc2.cokaen.wouldyouin.auth.application.dto.TokenResponse;
import org.ktc2.cokaen.wouldyouin.auth.application.dto.OauthLoginRequest;
import org.ktc2.cokaen.wouldyouin.auth.application.dto.OauthSignupRequest;
import org.ktc2.cokaen.wouldyouin.member.persist.AccountType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    // 로컬 계정의 회원가입 처리
    @PostMapping("/signup/local")
    public ResponseEntity<ApiResponseBody<TokenResponse>> signupLocal(@RequestBody LocalSignupRequest signupRequest) {
        return ApiResponse.created(authService.localSignup(signupRequest));
    }

    // 소셜 계정의 회원가입 처리
    @PostMapping("/signup/oauth/{accountType}")
    public ResponseEntity<ApiResponseBody<TokenResponse>> signupOauth(@PathVariable("accountType") AccountType accountType, @RequestBody OauthSignupRequest oauthSignupRequest) {
        return ApiResponse.created(authService.oauthSignup(accountType, oauthSignupRequest));
    }

    // 로컬 계정의 로그인 처리
    @PostMapping("/login/local")
    public ResponseEntity<ApiResponseBody<TokenResponse>> loginLocal(@RequestBody LocalLoginRequest request) {
        return ApiResponse.ok(authService.localLogin(request));
    }

    // 소셜 계정의 로그인 처리
    @PostMapping("/login/oauth/{accountType}")
    public ResponseEntity<ApiResponseBody<TokenResponse>> loginOauth(@PathVariable("accountType") AccountType accountType, @RequestBody OauthLoginRequest request) {
        return ApiResponse.ok(authService.oauthLogin(accountType, request));
    }
}
