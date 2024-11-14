package org.ktc2.cokaen.wouldyouin.member.api.dto.relationResponse;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.ktc2.cokaen.wouldyouin.member.persist.Member;

@Getter
@Builder
@EqualsAndHashCode
@ToString
public class ReservationMemberResponse {

    private Long memberId;
    private String email;
    private String nickname;
    private String phone;
    private String gender;

    public static ReservationMemberResponse from(Member member) {
        return ReservationMemberResponse.builder()
            .memberId(member.getId())
            .email(member.getEmail())
            .nickname(member.getNickname())
            .phone(member.getPhone())
            .gender(member.getGender())
            .build();
    }
}
