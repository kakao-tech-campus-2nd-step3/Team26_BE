package org.ktc2.cokaen.wouldyouin.controller.member.dto;

import lombok.Builder;
import lombok.Getter;
import org.ktc2.cokaen.wouldyouin.domain.Area;
import org.ktc2.cokaen.wouldyouin.domain.MemberType;
import org.ktc2.cokaen.wouldyouin.domain.member.AbstractMember;
import org.ktc2.cokaen.wouldyouin.domain.member.Curator;
import org.ktc2.cokaen.wouldyouin.domain.member.Host;
import org.ktc2.cokaen.wouldyouin.domain.member.Member;

import java.util.List;

@Getter
@Builder
public class MemberResponse {

    private Long memberId;
    private String nickname;
    private String phoneNumber;
    private String profileUrl;
    private String memberType;

    private Area area;
    private String gender;

    private String intro;
    private Integer followers;

    private List<String> hashtag;

    private static MemberResponseBuilder responseBase(AbstractMember memberBase) {
        return MemberResponse.builder()
            .memberId(memberBase.getId())
            .nickname(memberBase.getNickname())
            .phoneNumber(memberBase.getPhone())
            .profileUrl(memberBase.getProfileImageUrl());
    }

    public static MemberResponse from(final Member member) {
        return responseBase(member)
            .memberType(MemberType.normal.name())
            .area(member.getArea())
            .gender(member.getGender())
            .build();
    }

    public static MemberResponse from(final Host host) {
        return responseBase(host)
            .memberType(MemberType.host.name())
            .intro(host.getIntro())
            .followers(host.getFollowers())
            .hashtag(host.getHashTagList())
            .build();
    }

    public static MemberResponse from(final Curator curator) {
        return responseBase(curator)
            .memberType(MemberType.curator.name())
            .area(curator.getArea())
            .gender(curator.getGender())
            .intro(curator.getIntro())
            .followers(curator.getFollowers())
            .build();
    }
}
