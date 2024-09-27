package org.ktc2.cokaen.wouldyouin.controller.event;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import org.ktc2.cokaen.wouldyouin.domain.Area;
import org.ktc2.cokaen.wouldyouin.domain.Category;
import org.ktc2.cokaen.wouldyouin.domain.Event;
import org.ktc2.cokaen.wouldyouin.domain.Location;

@Getter
@Builder(toBuilder = true)
public class EventRequest {

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

    public Event toEntity() {
        return Event.builder()
            .hostId(hostId)
            .title(title)
            .content(content)
            .area(area)
            .location(location)
            .startTime(startTime)
            .endTime(endTime)
            .price(price)
            .totalSeat(totalSeat)
            .leftSeat(leftSeat)
            .category(category)
            .build();
    }
}