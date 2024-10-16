package org.ktc2.cokaen.wouldyouin.global;

import java.time.LocalDateTime;
import org.ktc2.cokaen.wouldyouin._common.persist.Area;
import org.ktc2.cokaen.wouldyouin._common.persist.Category;
import org.ktc2.cokaen.wouldyouin._common.persist.Location;
import org.ktc2.cokaen.wouldyouin.event.api.dto.EventEditRequest;
import org.ktc2.cokaen.wouldyouin.event.api.dto.EventCreateRequest;
import org.ktc2.cokaen.wouldyouin.event.persist.Event;
import org.ktc2.cokaen.wouldyouin.reservation.application.dto.ReservationRequest;
import org.ktc2.cokaen.wouldyouin.reservation.persist.Reservation;

public class TestData {

    public static EventCreateRequest validEventCreateRequest;
    public static EventEditRequest validEventRequestToModify;
    public static Event validEvent;
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
            .leftSeat(50)
            .category(Category.밴드)
            .build();

        validEventRequestToModify = EventEditRequest.builder()
            .title("modifiedTitle")
            .content("modifiedContent")
            .area(Area.광주)
            .location(new Location(232.0, 143.0))
            .startTime(LocalDateTime.of(2024, 10, 2, 17, 0))
            .endTime(LocalDateTime.of(2024, 10, 2, 18, 0))
            .price(20000)
            .totalSeat(200)
            .leftSeat(100)
            .category(Category.뮤지컬)
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
            .leftSeat(50)
            .category(Category.밴드)
            .build();

        validReservationRequest =
            ReservationRequest.builder()
                .id(1L)
                .memberId(1L)
                .eventId(1L)
                .price(10000)
                .quantity(1)
                .build();

        validReservation =
            Reservation.builder()
                .memberId(1L)
                .eventId(1L)
                .price(10000)
                .quantity(3)
                .build();
    }
}
