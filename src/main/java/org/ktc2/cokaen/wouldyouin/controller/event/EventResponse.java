package org.ktc2.cokaen.wouldyouin.controller.event;

import java.time.LocalDateTime;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import org.ktc2.cokaen.wouldyouin.domain.Area;
import org.ktc2.cokaen.wouldyouin.domain.Category;
import org.ktc2.cokaen.wouldyouin.domain.Event;
import org.ktc2.cokaen.wouldyouin.domain.Location;

@Builder
public class EventResponse {

    private UUID id;
    private UUID hostId;
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

    public static EventResponse toEventResponse(Event event) {
        return new EventResponse(event.getId(), event.getHostId(), event.getTitle(),
            event.getContent(), event.getArea(), event.getLocation(), event.getStartTime(),
            event.getEndTime(), event.getPrice(), event.getTotalSeat(), event.getLeftSeat(),
            event.getCategory(), event.getExpired());
    }
}