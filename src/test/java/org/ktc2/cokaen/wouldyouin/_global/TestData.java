package org.ktc2.cokaen.wouldyouin._global;

import java.time.LocalDateTime;
import java.util.List;
import org.ktc2.cokaen.wouldyouin.Image.persist.CurationImage;
import org.ktc2.cokaen.wouldyouin.Image.persist.EventImage;
import org.ktc2.cokaen.wouldyouin.Image.persist.MemberImage;
import org.ktc2.cokaen.wouldyouin._common.api.SliceInfo;
import org.ktc2.cokaen.wouldyouin._common.vo.Area;
import org.ktc2.cokaen.wouldyouin._common.vo.Category;
import org.ktc2.cokaen.wouldyouin._common.vo.Location;
import org.ktc2.cokaen.wouldyouin.curation.api.dto.CurationCardRequest;
import org.ktc2.cokaen.wouldyouin.curation.api.dto.CurationCardResponse;
import org.ktc2.cokaen.wouldyouin.curation.api.dto.CurationCreateRequest;
import org.ktc2.cokaen.wouldyouin.curation.api.dto.CurationEditRequest;
import org.ktc2.cokaen.wouldyouin.curation.api.dto.CurationResponse;
import org.ktc2.cokaen.wouldyouin.curation.api.dto.CurationSliceResponse;
import org.ktc2.cokaen.wouldyouin.curation.persist.Curation;
import org.ktc2.cokaen.wouldyouin.event.api.dto.EventCreateRequest;
import org.ktc2.cokaen.wouldyouin.event.api.dto.EventEditRequest;
import org.ktc2.cokaen.wouldyouin.event.api.dto.relationResonse.CurationEventResponse;
import org.ktc2.cokaen.wouldyouin.event.api.dto.relationResonse.ReservationEventResponse;
import org.ktc2.cokaen.wouldyouin.event.persist.Event;
import org.ktc2.cokaen.wouldyouin.member.api.dto.relation.ReservationMemberResponse;
import org.ktc2.cokaen.wouldyouin.member.api.dto.relationResponse.CurationCuratorResponse;
import org.ktc2.cokaen.wouldyouin.member.persist.AccountType;
import org.ktc2.cokaen.wouldyouin.member.persist.Curator;
import org.ktc2.cokaen.wouldyouin.member.persist.Host;
import org.ktc2.cokaen.wouldyouin.member.persist.Member;
import org.ktc2.cokaen.wouldyouin.member.persist.MemberType;
import org.ktc2.cokaen.wouldyouin.reservation.api.dto.ReservationRequest;
import org.ktc2.cokaen.wouldyouin.reservation.api.dto.ReservationResponse;
import org.ktc2.cokaen.wouldyouin.reservation.api.dto.ReservationSliceResponse;
import org.ktc2.cokaen.wouldyouin.reservation.persist.Reservation;
import org.springframework.test.util.ReflectionTestUtils;

public class TestData {

    public static SliceInfo createSliceInfo() {
        return SliceInfo.builder()
            .sliceSize(10)
            .lastId(100L)
            .build();
    }

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

        public static EventImage createValidEventImage(Long id) {
            EventImage ret = EventImage.builder()
                .url("eventImageUrl")
                .size(10L)
                .extension(".jpg")
                .build();
            ReflectionTestUtils.setField(ret, "id", id);
            return ret;
        }

        public static CurationImage createValidCurationImage(Long id) {
            CurationImage ret = CurationImage.builder()
                .url("curationImageUrl")
                .size(10L)
                .extension(".jpg")
                .build();
            ReflectionTestUtils.setField(ret, "id", id);
            return ret;
        }
    }

    public static class MemberDomain {

        public static final long validMemberId = 1L;
        public static final long validCuratorId = 2L;
        public static final long validHostId = 3L;
        public static final long validWelcomeMemberId = 4L;

        public static Member createValidMember() {
            MemberImage memberImage = ImageDomain.createValidMemberImage(validMemberId);
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
            MemberImage memberImage = ImageDomain.createValidMemberImage(validCuratorId);
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
            ReflectionTestUtils.setField(ret, "hashtag", "#큐레이터#해시태그#입니다");
            ReflectionTestUtils.setField(memberImage, "baseMember", ret);
            return ret;
        }

        public static Host createValidHost() {
            MemberImage memberImage = ImageDomain.createValidMemberImage(validHostId);
            Host ret = Host.builder()
                .email("curator1@example.com")
                .nickname("nick_curator_12")
                .phone("010-4545-6767")
                .hashedPassword("hashed_password")
                .profileImage(memberImage)
                .build();
            ReflectionTestUtils.setField(ret, "Id", validHostId);
            ReflectionTestUtils.setField(ret, "intro", "주최자 자기소개입니다.");
            ReflectionTestUtils.setField(ret, "hashtag", "#주최자#해시태그#입니다");
            ReflectionTestUtils.setField(memberImage, "baseMember", ret);
            return ret;
        }

        public static Member createValidWelcomeMember() {
            MemberImage memberImage = ImageDomain.createValidMemberImage(validWelcomeMemberId);
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

    public static class EventDomain {

        public static Event createValidEvent() {
            Event validEvent = Event.builder()
                .title("title")
                .content("content")
                .area(Area.전체)
                .location(new Location(132.0, 43.0))
                .startTime(LocalDateTime.now())
                .endTime(LocalDateTime.now())
                .price(10000)
                .totalSeat(100)
                .images(List.of())
                .category(Category.밴드)
                .build();
            validEvent.setHost(MemberDomain.createValidHost());
            return validEvent;
        }

        public static EventCreateRequest createValidEventCreateRequest() {
            return EventCreateRequest.builder()
                .title("title")
                .content("content 조홍식씨 최소글자 20자라고 해놓고 안 지켰어요.")
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

        public static EventEditRequest createValidEventEditRequest() {
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
                .imageIds(List.of(1L, 2L))
                .build();
        }

        public static CurationEventResponse createValidCurationEventResponse() {
            return CurationEventResponse.builder()
                .id(1L)
                .title("title")
                .location(new Location(132.0, 43.0))
                .thumbnailImageUrl("thumbnailImageUrl")
                .hostProfileImageUrl("hostProfileImageUrl")
                .hostNickname("nick_curator_12")
                .build();
        }

        public static ReservationEventResponse createValidReservationEventResponse() {
            return ReservationEventResponse.builder()
                .id(1L)
                .title("title")
                .price(15000)
                .build();
        }
    }

    public static class ReservationDomain {

        public static ReservationRequest createValidReservationRequest() {
            return ReservationRequest.builder()
                .eventId(1L)
                .quantity(2)
                .build();
        }

        public static Reservation createValidReservation() {
            Reservation reservation = Reservation.builder()
                .member(MemberDomain.createValidMember())
                .event(EventDomain.createValidEvent())
                .price(15000)
                .quantity(2)
                .build();
            ReflectionTestUtils.setField(reservation, "id", 1L);
            return reservation;
        }

        public static ReservationResponse createValidReservationResponse() {
            return ReservationResponse.builder()
                .id(1L)
                .member(MemberDomain.createValidReservationMemberResponse())
                .event(EventDomain.createValidReservationEventResponse())
                .price(15000)
                .quantity(2)
                .reservationDate(LocalDateTime.of(2024, 3, 23, 0, 0))
                .build();
        }

        public static ReservationSliceResponse createValidReservationSliceResponse() {
            return ReservationSliceResponse.builder()
                .reservations(List.of(createValidReservationResponse()))
                .sliceInfo(TestData.createSliceInfo())
                .build();
        }
    }

    public static class CurationDomain {

        public static Curation createValidCuration() {
            return Curation.builder()
                .curator(MemberDomain.createValidCurator())
                .title("title")
                .content("content")
                .curationCards(List.of())
                .area(Area.전체)
                .hashTag(List.of("#해시태그1", "#해시태그2"))
                .events(List.of(EventDomain.createValidEvent()))
                .build();
        }

        public static CurationCardRequest createValidCurationCardRequest1() {
            return CurationCardRequest.builder()
                .subtitle("부제목1")
                .content("큐레이션 카드 내용1 입니다. 큐레이션 카드의 내용은 최소 20자 최대 1000자 입니다.")
                .imageIds(List.of(1L, 2L))
                .build();
        }

        public static CurationCardRequest createValidCurationCardRequest2() {
            return CurationCardRequest.builder()
                .subtitle("부제목2")
                .content("큐레이션 카드 내용2 입니다. 큐레이션 카드의 내용은 최소 20자 최대 1000자 입니다.")
                .imageIds(List.of(3L, 4L))
                .build();
        }

        public static CurationCardResponse createCurationCardResponse1() {
            return CurationCardResponse.builder()
                .subtitle("부제목1")
                .content("큐레이션 카드 내용1 입니다. 큐레이션 카드의 내용은 최소 20자 최대 1000자 입니다.")
                .imageUrls(List.of("image1.com", "image2.com"))
                .build();
        }

        public static CurationCardResponse createCurationCardResponse2() {
            return CurationCardResponse.builder()
                .subtitle("부제목2")
                .content("큐레이션 카드 내용2 입니다. 큐레이션 카드의 내용은 최소 20자 최대 1000자 입니다.")
                .imageUrls(List.of("image3.com", "image4.com"))
                .build();
        }

        public static CurationCreateRequest createValidCurationCreateRequest() {
            return CurationCreateRequest.builder()
                .title("큐레이션 제목1")
                .content("큐레이션 카드 내용1 입니다. 큐레이션 카드의 내용은 최소 20자 최대 1000자 입니다.")
                .curationCards(List.of(createValidCurationCardRequest1()))
                .area(Area.광주)
                .hashTag(List.of("#광주밴드", "#전남대"))
                .eventIds(List.of(1L, 2L))
                .build();
        }

        public static CurationEditRequest createValidCurationEditRequest() {
            return CurationEditRequest.builder()
                .title("큐레이션 제목2")
                .content("큐레이션 내용2 입니다.")
                .curationCards(List.of(createValidCurationCardRequest2()))
                .area(Area.서울)
                .hashTag(List.of("#서울밴드", "#서울대"))
                .eventIds(List.of(3L, 4L))
                .build();
        }

        public static CurationCuratorResponse createCurationCuratorResponse() {
            Curator curator = MemberDomain.createValidCurator();
            return CurationCuratorResponse.builder()
                .nickname(curator.getNickname())
                .email(curator.getEmail())
                .phone(curator.getPhone())
                .profileImageUrl(curator.getProfileImageUrl())
                .intro(curator.getIntro())
                .likes(curator.getLikes())
                .hashtags(curator.getHashTagList())
                .build();
        }

        public static CurationResponse createValidCurationResponse() {
            return CurationResponse.builder()
                .curator(createCurationCuratorResponse())
                .title("title")
                .content("content")
                .curationCards(List.of(createCurationCardResponse1()))
                .area(Area.전체)
                .hashTag(List.of("#해시태그1", "#해시태그2"))
                .eventsInfo(List.of(EventDomain.createValidCurationEventResponse()))
                .build();
        }

        public static CurationSliceResponse createValidCurationSliceResponse() {
            return CurationSliceResponse.builder()
                .curations(List.of(createValidCurationResponse()))
                .sliceInfo(TestData.createSliceInfo())
                .build();
        }
    }
}
