package org.ktc2.cokaen.wouldyouin.reservation.api.dto;

import java.util.List;
import lombok.Builder;
import lombok.Getter;
import org.ktc2.cokaen.wouldyouin._common.api.SliceInfo;

@Getter
@Builder
public class ReservationSliceResponse {

    private List<ReservationResponse> reservations;
    private SliceInfo sliceInfo;

    public static ReservationSliceResponse of(List<ReservationResponse> reservations, int size, Long lastId) {
        return ReservationSliceResponse.builder()
            .reservations(reservations)
            .sliceInfo(SliceInfo.builder()
                .sliceSize(size)
                .lastId(lastId)
                .build())
            .build();
    }
}
