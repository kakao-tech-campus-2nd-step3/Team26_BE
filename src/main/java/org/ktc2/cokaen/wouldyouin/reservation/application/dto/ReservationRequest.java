package org.ktc2.cokaen.wouldyouin.reservation.application.dto;

import lombok.Builder;
import lombok.Getter;
import org.ktc2.cokaen.wouldyouin.reservation.persist.Reservation;

@Getter
@Builder(toBuilder = true)
public class ReservationRequest {

    private Long id;
    private Long memberId;
    private Long eventId;
    private Integer price;
    private Integer quantity;

    public Reservation toEntity() {
        return Reservation.builder()
            .memberId(memberId)
            .eventId(eventId)
            .price(price)
            .quantity(quantity)
            .build();
    }
}