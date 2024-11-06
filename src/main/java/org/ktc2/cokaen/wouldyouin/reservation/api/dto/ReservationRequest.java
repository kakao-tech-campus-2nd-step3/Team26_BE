package org.ktc2.cokaen.wouldyouin.reservation.api.dto;

import lombok.Builder;
import lombok.Getter;
import org.ktc2.cokaen.wouldyouin.event.persist.Event;
import org.ktc2.cokaen.wouldyouin.member.persist.Member;
import org.ktc2.cokaen.wouldyouin.reservation.persist.Reservation;

@Getter
@Builder(toBuilder = true)
public class ReservationRequest {

    private Long eventId;
    private Integer price;
    private Integer quantity;

    public Reservation toEntity(Member member, Event event) {
        return Reservation.builder()
            .member(member)
            .event(event)
            .price(price)
            .quantity(quantity)
            .build();
    }
}