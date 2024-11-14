package org.ktc2.cokaen.wouldyouin._global.testdata;

import java.time.LocalDateTime;
import java.util.List;
import org.ktc2.cokaen.wouldyouin._global.testdata.CurationData.R;
import org.ktc2.cokaen.wouldyouin._global.testdata.CurationData.curation1.entity;
import org.ktc2.cokaen.wouldyouin._global.testdata.MemberData.response.reservation1Member1;
import org.ktc2.cokaen.wouldyouin.curation.persist.Curation;
import org.ktc2.cokaen.wouldyouin.event.api.dto.relationResonse.ReservationEventResponse;
import org.ktc2.cokaen.wouldyouin.event.persist.Event;
import org.ktc2.cokaen.wouldyouin.member.api.dto.relationResponse.ReservationMemberResponse;
import org.ktc2.cokaen.wouldyouin.member.persist.Member;
import org.ktc2.cokaen.wouldyouin.payment.dto.KakaoPayResponse;
import org.ktc2.cokaen.wouldyouin.reservation.api.dto.KakaoPayReservationResponse;
import org.ktc2.cokaen.wouldyouin.reservation.api.dto.ReservationRequest;
import org.ktc2.cokaen.wouldyouin.reservation.api.dto.ReservationResponse;
import org.ktc2.cokaen.wouldyouin.reservation.api.dto.ReservationSliceResponse;
import org.ktc2.cokaen.wouldyouin.reservation.persist.Reservation;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.test.util.ReflectionTestUtils;

public class ReservationData {

    public static class R {

        public static final int page = 0;
        public static final int pageSize = 10;
        public static final Long lastId = 1L;
        public static final Pageable pageable = PageRequest.of(0, 10);

        public static class reservation1 {

            public static final Long id = 1L;
            public static final Integer price = _Relation.event().getPrice();
            public static final Integer quantity = 2;
            public static final LocalDateTime reservationDate = LocalDateTime.of(2024, 3, 23, 0, 0);
            public static final ReservationMemberResponse memberResponse = reservation1Member1.get();
            public static final ReservationEventResponse eventResponse = EventData.response.reservationEvent.createValidReservationEventResponse();

            public static class _Relation {

                public static Member member() {
                    return MemberData.normal1.entity.get();
                }

                public static Event event() {
                    return EventData.event1.entity.get();
                }
            }
        }
    }

    public static class reservation1 {

        public static class entity {

            public static Reservation get() {
                Reservation ret = Reservation.builder()
                    .member(R.reservation1._Relation.member())
                    .event(R.reservation1._Relation.event())
                    .price(R.reservation1.price)
                    .quantity(R.reservation1.quantity)
                    .build();
                ReflectionTestUtils.setField(ret, "id", R.reservation1.id);
                ReflectionTestUtils.setField(ret, "reservationDate",
                    R.reservation1.reservationDate);
                return ret;
            }
        }

        public static class request {

            public static ReservationRequest get() {
                return ReservationRequest.builder()
                    .eventId(R.reservation1._Relation.event().getId())
                    .quantity(R.reservation1.quantity)
                    .build();
            }
        }

        public static class response {

            public static ReservationResponse get() {
                return ReservationResponse.from(reservation1.entity.get());
            }

        }

        public static class kakaoPayResponse {

            public static KakaoPayResponse get() {
                return new KakaoPayResponse(
                    "10L", "nextRedirectAppUrl", "nextRedirectMobileUrl",
                    "nextRedirectPcUrl", "androidAppScheme", "ios_app_scheme",
                    LocalDateTime.of(2024, 3, 23, 0, 0));
            }
        }

        public static class kakaoPayReservationResponse {

            public static ReservationResponse reservationResponse = ReservationResponse.from(reservation1.entity.get());
            public static KakaoPayResponse kakaoPayResponse = ReservationData.reservation1.kakaoPayResponse.get();

            public static KakaoPayReservationResponse get() {
                return KakaoPayReservationResponse.from(reservationResponse, kakaoPayResponse);
            }
        }
    }

    public static class ReservationSlice {

        public static Slice<Reservation> get() {
            return new SliceImpl<>(List.of(ReservationData.reservation1.entity.get()), PageRequest.of(0, 10), true);
        }
    }

    public static class sliceResponse {

        public static ReservationSliceResponse get() {
            return ReservationSliceResponse.builder()
                .reservations(List.of(
                    ReservationData.reservation1.response.get()))
                .sliceInfo(CommonData.sliceInfo.reservation.get())
                .build();
        }
    }
}
