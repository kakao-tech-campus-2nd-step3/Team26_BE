package org.ktc2.cokaen.wouldyouin.member.api.dto;

import java.util.List;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.ktc2.cokaen.wouldyouin._common.vo.Area;
import org.ktc2.cokaen.wouldyouin.member.persist.BaseMember;
import org.ktc2.cokaen.wouldyouin.member.persist.Curator;
import org.ktc2.cokaen.wouldyouin.member.persist.Gender;
import org.ktc2.cokaen.wouldyouin.member.persist.Host;
import org.ktc2.cokaen.wouldyouin.member.persist.Member;
import org.ktc2.cokaen.wouldyouin.member.persist.MemberType;

@Getter
@EqualsAndHashCode
@ToString
@Builder
public class MemberResponse {

    private Long memberId;
    private String nickname;
    private String phoneNumber;
    private String profileUrl;
    private String profileThumbnailUrl;
    private MemberType memberType;

    private Area area;
    private Gender gender;

    private String intro;
    private Integer likes;

    private List<String> hashtag;

    private static MemberResponseBuilder responseBase(BaseMember baseMember, String profileUrl) {
        return MemberResponse.builder()
            .memberId(baseMember.getId())
            .nickname(baseMember.getNickname())
            .phoneNumber(baseMember.getPhone())
            .profileUrl(profileUrl)
            .profileThumbnailUrl(baseMember.getProfileImageThumbnailUrl());

    }

    // TODO: normal member임에도 불구, curator 형식이 호출되는 현상 수정필요
    public static MemberResponse from(final Member member, String profileUrl) {
        return responseBase(member, profileUrl)
            .memberType(member.getMemberType())
            .area(member.getArea())
            .gender(member.getGender())
            .build();
    }

    public static MemberResponse from(final Host host, String profileUrl) {
        return responseBase(host, profileUrl)
            .memberType(host.getMemberType())
            .intro(host.getIntro())
            .likes(host.getLikes())
            .hashtag(host.getHashtags())
            .build();
    }

    public static MemberResponse from(final Curator curator, String profileUrl) {
        return responseBase(curator, profileUrl)
            .memberType(curator.getMemberType())
            .area(curator.getArea())
            .gender(curator.getGender())
            .intro(curator.getIntro())
            .likes(curator.getLikes())
            .build();
    }
}
