package org.ktc2.cokaen.wouldyouin.global;

import java.time.LocalDateTime;
import java.util.List;
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

    public static class ImageDomain {

        public static MemberImage createValidMemberImage(Long id) {
            MemberImage ret = MemberImage.builder()
                .url("memberImageUrl")
                .size(10L)
                .extension(".jpg")
                .build();
            ReflectionTestUtils.setField(ret, "id", id);
            return ret;
        }
    }

    public static class MemberDomain {

        public static Member createValidMember() {
            MemberImage memberImage = ImageDomain.createValidMemberImage(1L);
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
            MemberImage memberImage = ImageDomain.createValidMemberImage(4L);
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
            MemberImage memberImage = ImageDomain.createValidMemberImage(3L);
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
            MemberImage memberImage = ImageDomain.createValidMemberImage(2L);
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
    public static class EventDomain {

        public static Event createValidEvent () {
            Event validEvent = Event.builder()
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
            validEvent.setHost(MemberDomain.createValidHost());
            return validEvent;
        }

        public static EventCreateRequest createValidEventCreateRequest() {
            return EventCreateRequest.builder()
                .title("title")
                .content("content 조홍식씨 최소글자 20자라고 해놓고 안 지켰어요. ")
                .area(Area.전체)
                .location(new Location(132.0, 43.0))
                .startTime(LocalDateTime.of(2025, 10, 1, 9, 0))
                .endTime(LocalDateTime.of(2025, 10, 1, 10, 0))
                .price(10000)
                .totalSeat(100)
                .category(Category.밴드)
                .imageIds(List.of())
                .build();
        }

        public static EventEditRequest createValidEventEditRequest () {
            return EventEditRequest.builder()
                .title("modifiedTitle")
                .content("modifiedContent 조홍식씨 최소글자 20자라고 해놓고 안 지켰어요. ")
                .area(Area.광주)
                .location(new Location(232.0, 143.0))
                .startTime(LocalDateTime.of(2024, 10, 2, 17, 0))
                .endTime(LocalDateTime.of(2024, 10, 2, 18, 0))
                .price(20000)
                .totalSeat(200)
                .category(Category.뮤지컬)
                .imageIds(List.of())
                .build();
        }
    }

    public static class ReservationDomain {

        public static ReservationRequest createValidReservationRequest () {
            return ReservationRequest.builder()
                .eventId(1L)
                .price(10000)
                .quantity(1)
                .build();
        }

        public static Reservation createValidReservation () {
            return  Reservation.builder()
                .member(MemberDomain.createValidMember())
                .event(EventDomain.createValidEvent())
                .price(10000)
                .quantity(3)
                .build();
        }
    }

}
