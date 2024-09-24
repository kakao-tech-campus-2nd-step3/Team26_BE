package org.ktc2.cokaen.wouldyouin.controller.event;

import java.time.LocalDateTime;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.ktc2.cokaen.wouldyouin.domain.Area;
import org.ktc2.cokaen.wouldyouin.domain.Category;
import org.ktc2.cokaen.wouldyouin.domain.Location;

@Getter
@Builder
public class EventRequest {

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
}