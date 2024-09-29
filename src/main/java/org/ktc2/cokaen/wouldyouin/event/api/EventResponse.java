package org.ktc2.cokaen.wouldyouin.event.api;

import java.time.LocalDateTime;
import lombok.Builder;
import org.ktc2.cokaen.wouldyouin._common.persist.Area;
import org.ktc2.cokaen.wouldyouin._common.persist.Category;
import org.ktc2.cokaen.wouldyouin.event.persist.Event;
import org.ktc2.cokaen.wouldyouin._common.persist.Location;

@Builder
public class EventResponse {

    private Long id;
    private Long hostId;
    private String title;
    private String content;
    private Area area;
    private Location location;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Integer price;
    private Integer totalSeat;
    private Integer leftSeat;
    private Category category;
    private Boolean expired;

    public static EventResponse from(Event event) {
        return EventResponse.builder()
            .id(event.getId())
            .hostId(event.getHostId())
            .title(event.getTitle())
            .content(event.getContent())
            .area(event.getArea())
            .location(event.getLocation())
            .startTime(event.getStartTime())
            .endTime(event.getEndTime())
            .price(event.getPrice())
            .totalSeat(event.getTotalSeat())
            .leftSeat(event.getLeftSeat())
            .category(event.getCategory())
            .expired(event.getStartTime().isAfter(LocalDateTime.now()))
            .build();
    }
}