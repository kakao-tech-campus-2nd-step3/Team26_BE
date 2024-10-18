package org.ktc2.cokaen.wouldyouin.auth.temp;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TempLoginController {

    @Value("${oauth.kakao.client.id}")
    private String kakaoClientId;

    @Value("${oauth.kakao.redirect_uri}")
    private String kakaoRedirectUri;

    @Value("${oauth.google.client.id}")
    private String googleClientId;

    @Value("${oauth.google.redirect_uri}")
    private String googleRedirectUri;

    @GetMapping("/social-login-test")
    public String LoginPage(Model model) {

        String googleSocialLoginUri = "https://accounts.google.com/o/oauth2/auth" +
            "?client_id=" + googleClientId +
            "&redirect_uri=" + googleRedirectUri +
            "&response_type=code&scope=email profile";

        String kakaoSocialLoginUri = "https://kauth.kakao.com/oauth/authorize" +
            "?client_id=" + kakaoClientId +
            "&redirect_uri=" + kakaoRedirectUri +
            "&response_type=code";

        model.addAttribute("google_social_login_uri", googleSocialLoginUri);
        model.addAttribute("kakao_social_login_uri", kakaoSocialLoginUri);

        return "social-login-test";
    }
}
