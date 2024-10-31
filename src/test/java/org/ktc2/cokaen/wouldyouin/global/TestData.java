package org.ktc2.cokaen.wouldyouin.global;

import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import org.ktc2.cokaen.wouldyouin.Image.persist.MemberImage;
import org.ktc2.cokaen.wouldyouin._common.persist.Area;
import org.ktc2.cokaen.wouldyouin._common.persist.Category;
import org.ktc2.cokaen.wouldyouin._common.persist.Location;
import org.ktc2.cokaen.wouldyouin.event.api.dto.EventEditRequest;
import org.ktc2.cokaen.wouldyouin.event.api.dto.EventCreateRequest;
import org.ktc2.cokaen.wouldyouin.event.persist.Event;
import org.ktc2.cokaen.wouldyouin.member.persist.AccountType;
import org.ktc2.cokaen.wouldyouin.member.persist.Host;
import org.ktc2.cokaen.wouldyouin.member.persist.MemberType;
import org.ktc2.cokaen.wouldyouin.reservation.application.dto.ReservationRequest;
import org.ktc2.cokaen.wouldyouin.reservation.persist.Reservation;

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
}
