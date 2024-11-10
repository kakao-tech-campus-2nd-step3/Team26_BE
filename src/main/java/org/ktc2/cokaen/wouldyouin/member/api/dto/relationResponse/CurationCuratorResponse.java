package org.ktc2.cokaen.wouldyouin.member.api.dto.relationResponse;

import java.util.List;
import lombok.Builder;
import lombok.Getter;
import org.ktc2.cokaen.wouldyouin.member.persist.Curator;

@Builder
@Getter
public class CurationCuratorResponse {

    private String nickname;
    private String email;
    private String phone;
    private String profileImageUrl;
    private String intro;
    private Integer likes;
    private List<String> hashtags;

    public static CurationCuratorResponse from(Curator curator) {
        return
            CurationCuratorResponse.builder()
                .nickname(curator.getNickname())
                .email(curator.getEmail())
                .phone(curator.getPhone())
                .profileImageUrl(curator.getProfileImageUrl())
                .intro(curator.getIntro())
                .likes(curator.getLikes())
                .hashtags(curator.getHashTagList())
                .build();
    }
}
