package org.ktc2.cokaen.wouldyouin.member.application.dto.relationResponse;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ReservationMemberResponse {

    private Long id;
    private String email;
    private String nickname;
    private String phone;
    private String gender;
}
