package org.ktc2.cokaen.wouldyouin.auth.api.dto;

import org.ktc2.cokaen.wouldyouin.member.persist.AccountType;

public record SocialLoginRequest(AccountType accountType, String token) {

}
