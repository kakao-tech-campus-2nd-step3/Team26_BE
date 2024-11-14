package org.ktc2.cokaen.wouldyouin.payment.dto;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.ktc2.cokaen.wouldyouin.reservation.persist.Reservation;

@Getter
@Builder
@EqualsAndHashCode
@ToString
public class KakaoPayRequest {

    private String reservationId;
    private String hostId;
    private String eventName;
    private String quantity;
    private String totalAmount;
    private String taxFreeAmount;

    public static KakaoPayRequest from(Reservation reservation) {
        return KakaoPayRequest.builder()
            .reservationId(reservation.getId().toString())
            .hostId(reservation.getEvent().getHost().getId().toString())
            .eventName(reservation.getEvent().getTitle())
            .quantity(reservation.getQuantity().toString())
            .totalAmount((reservation.getPrice() * reservation.getQuantity()) + "")
            .taxFreeAmount("0")
            .build();
    }
}