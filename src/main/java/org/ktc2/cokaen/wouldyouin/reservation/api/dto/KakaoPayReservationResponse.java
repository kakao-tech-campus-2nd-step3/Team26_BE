package org.ktc2.cokaen.wouldyouin.reservation.api.dto;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.ktc2.cokaen.wouldyouin.payment.dto.KakaoPayResponse;

@Builder
@Getter
@EqualsAndHashCode
@ToString
public class KakaoPayReservationResponse {

    private ReservationResponse reservationResponse;
    private KakaoPayResponse kakaoPayResponse;

    public static KakaoPayReservationResponse from(ReservationResponse reservationResponse, KakaoPayResponse kakaoPayResponse) {
        return KakaoPayReservationResponse.builder()
            .reservationResponse(reservationResponse)
            .kakaoPayResponse(kakaoPayResponse)
            .build();
    }
}
