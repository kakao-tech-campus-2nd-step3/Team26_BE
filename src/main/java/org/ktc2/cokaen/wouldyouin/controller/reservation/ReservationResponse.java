package org.ktc2.cokaen.wouldyouin.controller.reservation;

import java.time.LocalDateTime;
import lombok.Builder;
import org.ktc2.cokaen.wouldyouin.domain.Reservation;

@Builder
public class ReservationResponse {

    private Long id;
    private Long memberId;
    private Long eventId;
    private Integer price;
    private Integer quantity;
    private LocalDateTime reservationDate;

    public static ReservationResponse from(Reservation reservation) {
        return ReservationResponse.builder()
            .id(reservation.getId())
            .memberId(reservation.getMemberId())
            .eventId(reservation.getEventId())
            .price(reservation.getPrice())
            .quantity(reservation.getQuantity())
            .reservationDate(reservation.getReservationDate())
            .build();
    }
}
