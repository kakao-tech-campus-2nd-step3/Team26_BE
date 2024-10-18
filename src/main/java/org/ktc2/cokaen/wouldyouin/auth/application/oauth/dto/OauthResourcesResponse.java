package org.ktc2.cokaen.wouldyouin.auth.application.oauth.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@Builder
public class OauthResourcesResponse {

    private final String nickname;
    private final String email;
    private final String socialId;
    private final String profileImageUrl;

    @Override
    public String toString() {
        return "OauthResourcesResponse{" +
            "nickname='" + nickname + '\'' +
            ", email='" + email + '\'' +
            ", socialId='" + socialId + '\'' +
            ", profileImageUrl='" + profileImageUrl + '\'' +
            '}';
    }
}
