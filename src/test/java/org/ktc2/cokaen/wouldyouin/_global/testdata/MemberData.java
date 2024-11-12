package org.ktc2.cokaen.wouldyouin._global.testdata;

import java.util.List;
import org.ktc2.cokaen.wouldyouin.Image.persist.MemberImage;
import org.ktc2.cokaen.wouldyouin._common.vo.Area;
import org.ktc2.cokaen.wouldyouin.member.api.dto.relation.ReservationMemberResponse;
import org.ktc2.cokaen.wouldyouin.member.persist.AccountType;
import org.ktc2.cokaen.wouldyouin.member.persist.Curator;
import org.ktc2.cokaen.wouldyouin.member.persist.Host;
import org.ktc2.cokaen.wouldyouin.member.persist.Member;
import org.ktc2.cokaen.wouldyouin.member.persist.MemberType;
import org.springframework.test.util.ReflectionTestUtils;

public class MemberData {

    public static final long validMemberId = 101L;
    public static final long validCuratorId = 102L;
    public static final long validHostId = 103L;
    public static final long validWelcomeMemberId = 104L;
    public static final long validAdminId = 105L;

    public static Member createValidMember() {
        MemberImage memberImage = ImageData.createValidMemberImage();
        Member ret = Member.builder()
            .accountType(AccountType.kakao)
            .email("member1@example.com")
            .nickname("nick_normal_123")
            .phone("010-1112-2233")
            .profileImage(memberImage)
            .area(Area.광주)
            .gender("Men")
            .socialId("100100100100100")
            .build();
        ReflectionTestUtils.setField(ret, "Id", validMemberId);
        ReflectionTestUtils.setField(ret, "memberType", MemberType.normal);
        ReflectionTestUtils.setField(memberImage, "baseMember", ret);
        return ret;
    }

    public static Curator createValidCurator() {
        MemberImage memberImage = ImageData.createValidMemberImage();
        Curator ret = Curator.curatorBuilder()
            .accountType(AccountType.google)
            .email("curator1@example.com")
            .nickname("nick_curator_12")
            .phone("010-4545-6767")
            .profileImage(memberImage)
            .area(Area.광주)
            .gender("Women")
            .socialId("200200200200200")
            .build();
        ReflectionTestUtils.setField(ret, "Id", validCuratorId);
        ReflectionTestUtils.setField(ret, "intro", "큐레이터 자기소개입니다.");
        ReflectionTestUtils.setField(ret, "hashtags", List.of("#큐레이터", "#해시태그", "#입니다"));
        ReflectionTestUtils.setField(memberImage, "baseMember", ret);
        return ret;
    }

    public static Host createValidHost() {
        MemberImage memberImage = ImageData.createValidMemberImage();
        Host ret = Host.builder()
            .email("curator1@example.com")
            .nickname("nick_curator_12")
            .phone("010-4545-6767")
            .hashedPassword("hashed_password")
            .profileImage(memberImage)
            .build();
        ReflectionTestUtils.setField(ret, "Id", validHostId);
        ReflectionTestUtils.setField(ret, "intro", "주최자 자기소개입니다.");
        ReflectionTestUtils.setField(ret, "hashtags", List.of("#주최자", "#해시태그", "#입니다"));
        ReflectionTestUtils.setField(memberImage, "baseMember", ret);
        return ret;
    }

    public static Member createValidWelcomeMember() {
        MemberImage memberImage = ImageData.createValidMemberImage();
        Member ret = Member.builder()
            .accountType(AccountType.kakao)
            .email("member2@example.com")
            .nickname("nick_normal_333")
            .phone("010-4414-1144")
            .profileImage(memberImage)
            .area(Area.서울)
            .gender("Men")
            .socialId("456456456456")
            .build();
        ReflectionTestUtils.setField(ret, "Id", validWelcomeMemberId);
        ReflectionTestUtils.setField(memberImage, "baseMember", ret);
        return ret;
    }

    public static ReservationMemberResponse createValidReservationMemberResponse() {
        return ReservationMemberResponse.builder()
            .id(validMemberId)
            .email("member1@example.com")
            .nickname("nick_normal_123")
            .phone("010-1112-2233")
            .gender("Men")
            .build();
    }
}