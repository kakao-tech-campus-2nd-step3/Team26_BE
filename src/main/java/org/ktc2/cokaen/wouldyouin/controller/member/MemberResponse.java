package org.ktc2.cokaen.wouldyouin.controller.member;

import java.util.List;
import java.util.UUID;
import lombok.Builder;
import lombok.Getter;
import org.ktc2.cokaen.wouldyouin.domain.Area;
import org.ktc2.cokaen.wouldyouin.domain.member.Member;

@Getter
@Builder
public class MemberResponse {

    private UUID memberId;
    private String nickname;
    private Area area;
    private String gender;
    private String phoneNumber;
    private String profileUrl;
    private String memberType;

    private String intro;
    private Integer number;
    private List<String> hashtag;
    private String curatorProfileUrl;

    public static MemberResponse from(final Member member) {
        return MemberResponse.builder()
            .memberId(member.getId())
            .nickname(member.getNickname())
            .area(member.getArea())
            .gender(member.getGender())
            .phoneNumber(member.getPhone())
            .profileUrl(member.getProfileImageUrl())
            .memberType(member.getUserType())
            .intro(null)
            .number(null)
            .hashtag(null)
            .curatorProfileUrl(null)
            .build();
    }
}
