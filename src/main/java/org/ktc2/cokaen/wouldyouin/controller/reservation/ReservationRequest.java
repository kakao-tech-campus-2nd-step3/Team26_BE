package org.ktc2.cokaen.wouldyouin.controller.reservation;

import java.util.UUID;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ReservationRequest {

    private UUID memberId;
    private UUID eventId;
    private Integer price;
    private Integer quantity;
}
