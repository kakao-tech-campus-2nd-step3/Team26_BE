package org.ktc2.cokaen.wouldyouin.reservation.api.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.ktc2.cokaen.wouldyouin.event.persist.Event;
import org.ktc2.cokaen.wouldyouin.member.persist.Member;
import org.ktc2.cokaen.wouldyouin.reservation.persist.Reservation;

@Getter
@Builder(toBuilder = true)
@EqualsAndHashCode
public class ReservationRequest {

    @NotNull(message = "이벤트 ID는 필수입니다.")
    private Long eventId;

    @NotNull(message = "수량은 필수입니다.")
    @Min(value = 1, message = "수량은 1개 이상이어야 합니다.")
    private Integer quantity;

    public Reservation toEntity(Member member, Event event) {
        return Reservation.builder()
            .member(member)
            .event(event)
            .price(event.getPrice() * quantity)
            .quantity(quantity)
            .build();
    }
}