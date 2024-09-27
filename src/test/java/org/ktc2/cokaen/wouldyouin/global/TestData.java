package org.ktc2.cokaen.wouldyouin.global;

import java.time.LocalDateTime;
import org.ktc2.cokaen.wouldyouin.controller.event.EventRequest;
import org.ktc2.cokaen.wouldyouin.controller.reservation.ReservationRequest;
import org.ktc2.cokaen.wouldyouin.domain.Area;
import org.ktc2.cokaen.wouldyouin.domain.Category;
import org.ktc2.cokaen.wouldyouin.domain.Location;

public class TestData {

    public static EventRequest validEventRequest;
    public static ReservationRequest validReservationRequest;

    static {
        validEventRequest = EventRequest.builder()
            .hostId(1L)
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
    }
}
