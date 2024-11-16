package org.ktc2.cokaen.wouldyouin._develop.api;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller()
@RequestMapping("/api/test")
public class TempLoginController {

    @Value("${SERVER_DOMAIN_NAME}")
    private String serverDomainName;

    @Value("${oauth.kakao.client.id}")
    private String kakaoClientId;

    @Value("${oauth.google.client.id}")
    private String googleClientId;

    @GetMapping("/social-login")
    public String LoginPage(Model model) {

        String googleSocialLoginUri = "https://accounts.google.com/o/oauth2/auth" +
            "?client_id=" + googleClientId +
            "&redirect_uri=" + serverDomainName + "/auth/redirect/social/google" +
            "&response_type=code&scope=email profile";

        String kakaoSocialLoginUri = "https://kauth.kakao.com/oauth/authorize" +
            "?client_id=" + kakaoClientId +
            "&redirect_uri=" + serverDomainName + "/auth/redirect/social/kakao" +
            "&response_type=code";

        model.addAttribute("google_social_login_uri", googleSocialLoginUri);
        model.addAttribute("kakao_social_login_uri", kakaoSocialLoginUri);

        return "social-login-test";
    }
}
