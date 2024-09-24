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
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @NotNull
    private UUID memberId;

    @NotNull
    private UUID eventId;

    @NotNull
    private Integer price;

    @NotNull
    private Integer quantity;

    @Column(nullable=false)
    private LocalDateTime reservationDate;

    @PrePersist
    private void prePersist() {
        reservationDate = LocalDateTime.now();
    }

    @Builder
    protected Reservation(UUID memberId, UUID eventId, Integer price, Integer quantity,
        LocalDateTime reservationDate) {
        this.memberId = memberId;
        this.eventId = eventId;
        this.price = price;
        this.quantity = quantity;
        this.reservationDate = reservationDate;
    }

    public static Reservation from(ReservationRequest reservationRequest) {
        return Reservation.builder()
            .memberId(reservationRequest.getMemberId())
            .eventId(reservationRequest.getEventId())
            .price(reservationRequest.getPrice())
            .quantity(reservationRequest.getQuantity())
            .build();
    }
}