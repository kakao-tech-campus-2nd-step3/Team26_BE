package org.ktc2.cokaen.wouldyouin.reservation.api.dto;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import org.ktc2.cokaen.wouldyouin.event.api.dto.relationResonse.ReservationEventResponse;
import org.ktc2.cokaen.wouldyouin.member.api.dto.relation.ReservationMemberResponse;
import org.ktc2.cokaen.wouldyouin.reservation.persist.Reservation;

@Builder
@Getter
public class ReservationResponse {

    private Long id;
    private ReservationMemberResponse member;
    private ReservationEventResponse event;
    private Integer price;
    private Integer quantity;
    private LocalDateTime reservationDate;

    public static ReservationResponse from(Reservation reservation) {
        return ReservationResponse.builder()
            .id(reservation.getId())
            .member(
                ReservationMemberResponse.builder()
                    .id(reservation.getMember().getId())
                    .email(reservation.getMember().getEmail())
                    .nickname(reservation.getMember().getNickname())
                    .phone(reservation.getMember().getPhone())
                    .gender(reservation.getMember().getGender())
                    .build()
            )
            .event(
                ReservationEventResponse.builder()
                    .id(reservation.getEvent().getId())
                    .title(reservation.getEvent().getTitle())
                    .price(reservation.getEvent().getPrice())
                    .build()
            )
            .price(reservation.getPrice())
            .quantity(reservation.getQuantity())
            .reservationDate(reservation.getReservationDate())
            .build();
    }
}