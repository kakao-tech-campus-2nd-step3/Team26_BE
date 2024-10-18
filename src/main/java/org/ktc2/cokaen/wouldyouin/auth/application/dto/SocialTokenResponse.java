package org.ktc2.cokaen.wouldyouin.auth.application.dto;

import lombok.Builder;

@Builder
public record SocialTokenResponse(Boolean isWelcomeMember, String token) {

}
