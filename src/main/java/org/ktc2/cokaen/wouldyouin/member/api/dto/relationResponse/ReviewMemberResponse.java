package org.ktc2.cokaen.wouldyouin.member.api.dto.relationResponse;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ReviewMemberResponse {

    private Long id;
    private String nickname;
}
