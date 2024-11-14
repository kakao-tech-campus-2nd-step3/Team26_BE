package org.ktc2.cokaen.wouldyouin.auth.api.dto;

import lombok.Builder;
import org.ktc2.cokaen.wouldyouin.member.persist.MemberType;

@Builder
public record SocialTokenResponse(Boolean isWelcomeMember, String token, Long memberId, MemberType memberType) {

}
