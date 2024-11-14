package org.ktc2.cokaen.wouldyouin.reservation.api.dto;

import java.util.List;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.ktc2.cokaen.wouldyouin._common.api.SliceInfo;
import org.ktc2.cokaen.wouldyouin.reservation.persist.Reservation;
import org.springframework.data.domain.Slice;

@Getter
@Builder
@EqualsAndHashCode
@ToString
public class ReservationSliceResponse {

    private List<ReservationResponse> reservations;
    private SliceInfo sliceInfo;

    public static ReservationSliceResponse from(Slice<Reservation> reservations, int size, Long lastId) {
        return ReservationSliceResponse.builder()
            .reservations(reservations.stream()
                .map(ReservationResponse::from).toList())
            .sliceInfo(SliceInfo.builder()
                .sliceSize(size)
                .lastId(lastId)
                .build())
            .build();
    }
}
