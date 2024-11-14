package org.ktc2.cokaen.wouldyouin.event.api.dto.relationResonse;

import java.util.List;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.ktc2.cokaen.wouldyouin._common.api.SliceInfo;

@Getter
@Builder
@EqualsAndHashCode
@ToString
public class ReviewEventSliceResponse {

    private List<ReviewEventResponse> reviewEvents;
    private SliceInfo sliceInfo;

    public static ReviewEventSliceResponse from(List<ReviewEventResponse> reviewEvents, int size,
        Long lastId) {
        return ReviewEventSliceResponse.builder()
            .reviewEvents(reviewEvents)
            .sliceInfo(SliceInfo.builder()
                .sliceSize(size)
                .lastId(lastId)
                .build())
            .build();
    }
}
