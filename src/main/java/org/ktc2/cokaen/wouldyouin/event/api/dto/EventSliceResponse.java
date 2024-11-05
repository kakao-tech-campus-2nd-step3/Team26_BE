package org.ktc2.cokaen.wouldyouin.event.api.dto;

import java.util.List;
import lombok.Builder;
import lombok.Getter;
import org.ktc2.cokaen.wouldyouin._common.api.SliceInfo;

@Getter
@Builder
public class EventSliceResponse {

    private List<EventResponse> events;
    private SliceInfo slice;

    public static EventSliceResponse of(List<EventResponse> eventResponses, int sliceSize, long lastId) {
        return EventSliceResponse.builder()
            .events(eventResponses)
            .slice(SliceInfo.builder()
                .sliceSize(sliceSize)
                .lastId(lastId)
                .build())
            .build();
    }
}