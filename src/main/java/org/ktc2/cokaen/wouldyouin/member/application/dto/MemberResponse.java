package org.ktc2.cokaen.wouldyouin.member.application.dto;

import lombok.Builder;
import lombok.Getter;
import org.ktc2.cokaen.wouldyouin._common.persist.Area;
import org.ktc2.cokaen.wouldyouin.member.persist.MemberType;
import org.ktc2.cokaen.wouldyouin.member.persist.BaseMember;
import org.ktc2.cokaen.wouldyouin.member.persist.Curator;
import org.ktc2.cokaen.wouldyouin.member.persist.Host;
import org.ktc2.cokaen.wouldyouin.member.persist.Member;

import java.util.List;

@Getter
@Builder
public class MemberResponse {

    private Long memberId;
    private String nickname;
    private String phoneNumber;
    private String profileUrl;
    private MemberType memberType;

    private Area area;
    private String gender;

    private String intro;
    private Integer followers;

    private List<String> hashtag;

    private static MemberResponseBuilder responseBase(BaseMember baseMember) {
        return MemberResponse.builder()
            .memberId(baseMember.getId())
            .nickname(baseMember.getNickname())
            .phoneNumber(baseMember.getPhone())
            .profileUrl(baseMember.getProfileImageUrl());
    }

    // TODO: normal member임에도 불구, curator 형식이 호출되는 현상 수정필요
    public static MemberResponse from(final Member member) {
        return responseBase(member)
            .memberType(MemberType.normal)
            .area(member.getArea())
            .gender(member.getGender())
            .build();
    }

    public static MemberResponse from(final Host host) {
        return responseBase(host)
            .memberType(MemberType.host)
            .intro(host.getIntro())
            .followers(host.getFollowers())
            .hashtag(host.getHashTagList())
            .build();
    }

    public static MemberResponse from(final Curator curator) {
        return responseBase(curator)
            .memberType(MemberType.curator)
            .area(curator.getArea())
            .gender(curator.getGender())
            .intro(curator.getIntro())
            .followers(curator.getFollowers())
            .build();
    }
}
