package org.ktc2.cokaen.wouldyouin.auth;

import org.ktc2.cokaen.wouldyouin._common.exception.BusinessException;
import org.ktc2.cokaen.wouldyouin._common.exception.ErrorCode;
import org.ktc2.cokaen.wouldyouin.member.persist.AccountType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthRedirectController {

    @Value("${oauth.kakao.redirect_uri}")
    private String kakaoRedirectUri;

    @Value("${oauth.google.redirect_uri}")
    private String googleRedirectUri;

    private String getRedirectUri(AccountType accountType) {
        switch(accountType) {
            case kakao:
                return kakaoRedirectUri;
            case google:
                return googleRedirectUri;
            default:
                throw new BusinessException("Invalid account type: " + accountType, ErrorCode.UNEXPECTED);
        }
    }

    @GetMapping("/auth/redirect/social/{accountType}")
    public String redirect(@PathVariable("accountType")AccountType accountType, @RequestParam("code") String code) {
        return "redirect:" + getRedirectUri(accountType) + "?code=" + code;
    }
}
