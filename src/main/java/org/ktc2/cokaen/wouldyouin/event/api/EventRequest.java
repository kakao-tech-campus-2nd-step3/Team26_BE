package org.ktc2.cokaen.wouldyouin.event.api;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import org.ktc2.cokaen.wouldyouin._common.persist.Area;
import org.ktc2.cokaen.wouldyouin._common.persist.Category;
import org.ktc2.cokaen.wouldyouin.event.persist.Event;
import org.ktc2.cokaen.wouldyouin._common.persist.Location;

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