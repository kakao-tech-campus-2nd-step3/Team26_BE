package org.ktc2.cokaen.wouldyouin.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.ktc2.cokaen.wouldyouin.controller.reservation.ReservationRequest;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private Long memberId;

    @NotNull
    private Long eventId;

    @NotNull
    private Integer price;

    @NotNull
    private Integer quantity;

    @NotNull
    private LocalDateTime reservationDate;

    @Builder
    protected Reservation(Long memberId, Long eventId, Integer price, Integer quantity) {
        this.memberId = memberId;
        this.eventId = eventId;
        this.price = price;
        this.quantity = quantity;
        this.reservationDate = LocalDateTime.now();
    }
}