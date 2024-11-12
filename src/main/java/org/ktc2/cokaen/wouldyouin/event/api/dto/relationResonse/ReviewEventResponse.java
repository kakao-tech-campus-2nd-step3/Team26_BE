package org.ktc2.cokaen.wouldyouin.event.api.dto.relationResonse;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import org.ktc2.cokaen.wouldyouin.event.persist.Event;

@Getter
@Builder
public class ReviewEventResponse {

    private Long eventId;
    private String title;
    private LocalDateTime startTime;
    private String thumbnailUrl;

    public static ReviewEventResponse from(Event event){
        return builder()
            .eventId(event.getId())
            .title(event.getTitle())
            .startTime(event.getStartTime())
            .thumbnailUrl(event.getThumbnailUrl())
            .build();
    }
}
