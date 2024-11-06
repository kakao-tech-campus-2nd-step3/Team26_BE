package org.ktc2.cokaen.wouldyouin.global;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import org.ktc2.cokaen.wouldyouin.Image.persist.MemberImage;
import org.ktc2.cokaen.wouldyouin._common.vo.Area;
import org.ktc2.cokaen.wouldyouin._common.vo.Category;
import org.ktc2.cokaen.wouldyouin._common.vo.Location;
import org.ktc2.cokaen.wouldyouin.event.api.dto.EventEditRequest;
import org.ktc2.cokaen.wouldyouin.event.api.dto.EventCreateRequest;
import org.ktc2.cokaen.wouldyouin.event.persist.Event;
import org.ktc2.cokaen.wouldyouin.member.persist.AccountType;
import org.ktc2.cokaen.wouldyouin.member.persist.Curator;
import org.ktc2.cokaen.wouldyouin.member.persist.Host;
import org.ktc2.cokaen.wouldyouin.member.persist.Member;
import org.ktc2.cokaen.wouldyouin.member.persist.MemberType;
import org.ktc2.cokaen.wouldyouin.reservation.api.dto.ReservationRequest;
import org.ktc2.cokaen.wouldyouin.reservation.persist.Reservation;
import org.springframework.test.util.ReflectionTestUtils;

public class TestData {

    public static EventCreateRequest validEventCreateRequest;
    public static EventEditRequest validEventEditRequest;
    public static Event validEvent;
    public static Host validHost;
    public static ReservationRequest validReservationRequest;
    public static Reservation validReservation;

    static {
        validEventCreateRequest = EventCreateRequest.builder()
            .hostId(1L)
            .title("title")
            .content("content")
            .area(Area.전체)
            .location(new Location(132.0, 43.0))
            .startTime(LocalDateTime.of(2024, 10, 1, 9, 0))
            .endTime(LocalDateTime.of(2024, 10, 1, 10, 0))
            .price(10000)
            .totalSeat(100)
            .category(Category.밴드)
            .imageIds(List.of())
            .build();

        validEventEditRequest = EventEditRequest.builder()
            .title("modifiedTitle")
            .content("modifiedContent")
            .area(Area.광주)
            .location(new Location(232.0, 143.0))
            .startTime(LocalDateTime.of(2024, 10, 2, 17, 0))
            .endTime(LocalDateTime.of(2024, 10, 2, 18, 0))
            .price(20000)
            .totalSeat(200)
            .category(Category.뮤지컬)
            .imageIds(List.of())
            .build();

        validEvent = Event.builder()
            .title("title")
            .content("content")
            .area(Area.전체)
            .location(new Location(132.0, 43.0))
            .startTime(LocalDateTime.now())
            .endTime(LocalDateTime.now())
            .price(10000)
            .totalSeat(100)
            .category(Category.밴드)
            .build();

        validHost = Host.builder()
            .nickname("nickname")
            .phone("010-1234-5678")
            .hashedPassword(UUID.randomUUID().toString())
            .build();
        validEvent.setHost(validHost);

        validReservationRequest =
            ReservationRequest.builder()
                .eventId(1L)
                .price(10000)
                .quantity(1)
                .build();

        validReservation =
            Reservation.builder()
                .member(null)
                .event(null)
                .price(10000)
                .quantity(3)
                .build();
    }

    public static class MemberDomain {

        public static MemberImage createValidMemberImage(Long id) {
            MemberImage ret = MemberImage.builder()
                .name("memberImage")
                .size(10L)
                .extension(".jpg")
                .build();
            ReflectionTestUtils.setField(ret, "id", id);
            return ret;
        }

        public static Member createValidMember() {
            MemberImage memberImage = createValidMemberImage(1L);
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
            ReflectionTestUtils.setField(ret, "Id", 1L);
            ReflectionTestUtils.setField(ret, "memberType", MemberType.normal);
            ReflectionTestUtils.setField(memberImage, "baseMember", ret);
            return ret;
        }

        public static Member createValidWelcomeMember() {
            MemberImage memberImage = createValidMemberImage(4L);
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
            ReflectionTestUtils.setField(ret, "Id", 4L);
            ReflectionTestUtils.setField(memberImage, "baseMember", ret);
            return ret;
        }

        public static Host createValidHost() {
            MemberImage memberImage = createValidMemberImage(3L);
            Host ret = Host.builder()
                .email("curator1@example.com")
                .nickname("nick_curator_12")
                .phone("010-4545-6767")
                .hashedPassword("hashed_password")
                .profileImage(memberImage)
                .build();
            ReflectionTestUtils.setField(ret, "Id", 3L);
            ReflectionTestUtils.setField(ret, "intro", "주최자 자기소개입니다.");
            ReflectionTestUtils.setField(ret, "hashtag", "#주최자#해시태그#입니다");
            ReflectionTestUtils.setField(memberImage, "baseMember", ret);
            return ret;
        }

        public static Curator createValidCurator() {
            MemberImage memberImage = createValidMemberImage(2L);
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
            ReflectionTestUtils.setField(ret, "Id", 2L);
            ReflectionTestUtils.setField(ret, "intro", "큐레이터 자기소개입니다.");
            ReflectionTestUtils.setField(ret, "hashtag", "#큐레이터#해시태그#입니다");
            ReflectionTestUtils.setField(memberImage, "baseMember", ret);
            return ret;
        }
    }
}
