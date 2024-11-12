package org.ktc2.cokaen.wouldyouin.member.api.dto.relationResponse;

import java.util.List;
import lombok.Builder;
import lombok.Getter;
import org.ktc2.cokaen.wouldyouin.member.persist.Host;

@Builder
@Getter
public class EventHostResponse {

    private Long hostId;
    private String nickname;
    private String email;
    private String phone;
    private String profileImageUrl;
    private String intro;
    private Integer likes;
    private List<String> hashtags;

    public static EventHostResponse from(Host host) {
        return EventHostResponse.builder()
            .hostId(host.getId())
            .nickname(host.getNickname())
            .email(host.getEmail())
            .phone(host.getPhone())
            .profileImageUrl(host.getProfileImage().getName())
            .intro(host.getIntro())
            .likes(host.getLikes())
            .hashtags(host.getHashtags())
            .build();
    }
}