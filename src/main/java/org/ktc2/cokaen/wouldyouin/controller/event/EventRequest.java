package org.ktc2.cokaen.wouldyouin.controller.event;

import java.time.LocalDateTime;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.ktc2.cokaen.wouldyouin.domain.Area;
import org.ktc2.cokaen.wouldyouin.domain.Category;

@Getter
@AllArgsConstructor
public class EventRequest {

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
}