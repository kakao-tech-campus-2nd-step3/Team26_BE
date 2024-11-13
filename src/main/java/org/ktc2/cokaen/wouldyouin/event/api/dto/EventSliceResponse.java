package org.ktc2.cokaen.wouldyouin.event.api.dto;

import java.util.List;
import lombok.Builder;
import lombok.Getter;
import org.ktc2.cokaen.wouldyouin._common.api.SliceInfo;

@Getter
@Builder
public class EventSliceResponse {

    private List<EventResponse> events;
    private SliceInfo sliceInfo;

    public static EventSliceResponse from(List<EventResponse> events, int size, Long lastId) {
        return EventSliceResponse.builder()
            .events(events)
            .sliceInfo(SliceInfo.builder()
                .sliceSize(size)
                .lastId(lastId)
                .build())
            .build();
    }
}