package org.ktc2.cokaen.wouldyouin._global;

import static org.ktc2.cokaen.wouldyouin._global.testdata.MemberData.createValidHost;
import static org.ktc2.cokaen.wouldyouin._global.testdata.MemberData.createValidMember;
import static org.ktc2.cokaen.wouldyouin._global.testdata.MemberData.createValidReservationMemberResponse;

import java.time.LocalDateTime;
import java.util.List;
import org.ktc2.cokaen.wouldyouin._common.api.SliceInfo;
import org.ktc2.cokaen.wouldyouin._common.vo.Area;
import org.ktc2.cokaen.wouldyouin._common.vo.Category;
import org.ktc2.cokaen.wouldyouin._common.vo.Location;
import org.ktc2.cokaen.wouldyouin.event.api.dto.EventCreateRequest;
import org.ktc2.cokaen.wouldyouin.event.api.dto.EventEditRequest;
import org.ktc2.cokaen.wouldyouin.event.api.dto.relationResonse.CurationEventResponse;
import org.ktc2.cokaen.wouldyouin.event.api.dto.relationResonse.ReservationEventResponse;
import org.ktc2.cokaen.wouldyouin.event.persist.Event;
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

    public static

    public static class EventDomain {

        public static Event createValidEvent() {
            Event validEvent = Event.builder()
                .title("title")
                .content("content")
                .area(Area.전체)
                .location(new Location(132.0, 43.0, "광주 북구 용봉로 77"))
                .startTime(LocalDateTime.now())
                .endTime(LocalDateTime.now())
                .price(10000)
                .totalSeat(100)
                .category(Category.밴드)
                .build();
            validEvent.setHost(createValidHost());
            ReflectionTestUtils.setField(validEvent, "id", 201L);
            return validEvent;
        }

        public static EventCreateRequest createValidEventCreateRequest() {
            return EventCreateRequest.builder()
                .title("title")
                .content("content 조홍식씨 최소글자 20자라고 해놓고 안 지켰어요.")
                .area(Area.전체)
                .location(new Location(55.0, 43.0, "광주 북구 용봉로 77"))
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
                .location(new Location(232.0, 143.0, "광주 북구 용봉로 77"))
                .startTime(LocalDateTime.of(2024, 10, 2, 17, 0))
                .endTime(LocalDateTime.of(2024, 10, 2, 18, 0))
                .price(20000)
                .totalSeat(200)
                .category(Category.뮤지컬)
                .imageIds(List.of())
                .build();
        }

        public static CurationEventResponse createValidCurationEventResponse() {
            return CurationEventResponse.builder()
                .id(1L)
                .title("title")
                .location(new Location(132.0, 43.0, "광주 북구 용봉로 77"))
                .thumbnailImageUrl("thumbnailImageUrl")
                .hostProfileImageUrl("wouldyouin.com/memberImage1.jpg")
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
                .member(createValidMember())
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
                .member(createValidReservationMemberResponse())
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
}