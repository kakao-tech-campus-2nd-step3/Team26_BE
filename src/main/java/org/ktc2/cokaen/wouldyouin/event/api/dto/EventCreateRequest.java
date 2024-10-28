package org.ktc2.cokaen.wouldyouin.event.api.dto;

import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import org.ktc2.cokaen.wouldyouin._common.persist.Area;
import org.ktc2.cokaen.wouldyouin._common.persist.Category;
import org.ktc2.cokaen.wouldyouin.event.persist.Event;
import org.ktc2.cokaen.wouldyouin._common.persist.Location;

@Getter
@Builder
public class EventCreateRequest {

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
    private List<Long> imageIds;

    public Event toEntity() {
        return Event.builder()
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