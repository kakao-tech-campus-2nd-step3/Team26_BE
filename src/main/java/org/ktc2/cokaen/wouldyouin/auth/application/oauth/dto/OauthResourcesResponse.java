package org.ktc2.cokaen.wouldyouin.auth.application.oauth.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@RequiredArgsConstructor
@Builder
@ToString
public class OauthResourcesResponse {

    private final String nickname;
    private final String email;
    private final String socialId;
    private final String profileImageUrl;
}
