package org.ktc2.cokaen.wouldyouin.event.api.dto.relationResonse;

import lombok.Builder;
import lombok.Getter;
import org.ktc2.cokaen.wouldyouin._common.vo.Location;
import org.ktc2.cokaen.wouldyouin.event.persist.Event;

@Getter
@Builder
public class ReservationEventResponse {

    private Long eventId;
    private String title;
    private Integer price;
    private Location location;
    private String thumbnailUrl;

    public static ReservationEventResponse from(Event event) {
        return ReservationEventResponse.builder()
            .eventId(event.getId())
            .title(event.getTitle())
            .price(event.getPrice())
            .location(event.getLocation())
            .thumbnailUrl(event.getThumbnailUrl())
            .build();
    }
}