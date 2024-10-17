package org.ktc2.cokaen.wouldyouin.event.api.dto.relationResonse;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ReservationEventResponse {

    private Long id;
    private String title;
    private Integer price;
}