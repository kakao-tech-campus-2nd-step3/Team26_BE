package org.ktc2.cokaen.wouldyouin.controller.event;

import java.time.LocalDateTime;
import java.util.UUID;
import lombok.AllArgsConstructor;
import org.ktc2.cokaen.wouldyouin.domain.Area;
import org.ktc2.cokaen.wouldyouin.domain.Category;
import org.ktc2.cokaen.wouldyouin.domain.Event;

@AllArgsConstructor
public class EventResponse {

    private UUID id;
    private UUID hostId;
    private String title;
    private String content;
    private Area area;
    private String location;
    private LocalDateTime start_time;
    private LocalDateTime end_time;
    private Integer price;
    private Integer totalSeat;
    private Integer leftSeat;
    private Category category;
    private Boolean expired;

    public static EventResponse toEventResponse(Event event) {
        return new EventResponse(event.getId(), event.getHostId(), event.getTitle(),
            event.getContent(), event.getArea(), event.getLocation(), event.getStart_time(),
            event.getEnd_time(), event.getPrice(), event.getTotalSeat(), event.getLeftSeat(),
            event.getCategory(), event.getExpired());
    }
}