package org.ktc2.cokaen.wouldyouin.member.application.dto.relationResponse;

import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class EventHostResponse {

    private String nickname;
    private String email;
    private String phone;
    private String profileImageUrl;
    private String intro;
    private Integer likes;
    private List<String> hashtags;
}