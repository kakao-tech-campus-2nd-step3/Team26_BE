package org.ktc2.cokaen.wouldyouin.event.api.dto;

import java.util.List;
import lombok.Builder;
import lombok.Getter;
import org.ktc2.cokaen.wouldyouin._common.api.SliceInfo;
import org.ktc2.cokaen.wouldyouin.event.persist.Event;
import org.springframework.data.domain.Slice;

@Getter
@Builder
public class EventSliceResponse {

    private List<EventResponse> events;
    private SliceInfo sliceInfo;

    public static EventSliceResponse from(Slice<Event> reservations, int size, Long lastId) {
        return EventSliceResponse.builder()
            .events(reservations.stream()
                .map(EventResponse::from).toList())
            .sliceInfo(SliceInfo.builder()
                .sliceSize(size)
                .lastId(lastId)
                .build())
            .build();
    }
}