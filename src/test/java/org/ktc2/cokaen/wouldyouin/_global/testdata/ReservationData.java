package org.ktc2.cokaen.wouldyouin._global.testdata;

import java.time.LocalDateTime;
import java.util.List;
import org.ktc2.cokaen.wouldyouin.event.api.dto.relationResonse.ReservationEventResponse;
import org.ktc2.cokaen.wouldyouin.event.persist.Event;
import org.ktc2.cokaen.wouldyouin.member.api.dto.relationResponse.ReservationMemberResponse;
import org.ktc2.cokaen.wouldyouin.member.persist.Member;
import org.ktc2.cokaen.wouldyouin.reservation.api.dto.ReservationRequest;
import org.ktc2.cokaen.wouldyouin.reservation.api.dto.ReservationResponse;
import org.ktc2.cokaen.wouldyouin.reservation.api.dto.ReservationSliceResponse;
import org.ktc2.cokaen.wouldyouin.reservation.persist.Reservation;
import org.springframework.test.util.ReflectionTestUtils;

public class ReservationData {

    public static class R {
        public static class reservation {
            public static final Long id = 1L;
            public static final Member member = MemberData.normal.entity.get();
            public static final Event event = EventData.event.entity.get();
            public static final Integer price = EventData.R.event.price;
            public static final Integer quantity = 2;
            public static final LocalDateTime reservationDate = LocalDateTime.of(2024, 3, 23, 0, 0);

            public static final ReservationMemberResponse memberResponse = MemberData.response.reservationMember.get();
            public static final ReservationEventResponse eventResponse = EventData.response.reservationEvent.createValidReservationEventResponse();
        }
    }

    public static class reservation {
        public static class entity {
            public static Reservation get() {
                Reservation ret = Reservation.builder()
                    .member(R.reservation.member)
                    .event(R.reservation.event)
                    .price(R.reservation.price)
                    .quantity(R.reservation.quantity)
                    .build();
                ReflectionTestUtils.setField(ret, "id", R.reservation.id);
                ReflectionTestUtils.setField(ret, "reservationDate", R.reservation.reservationDate);
                return ret;
            }
        }
        public static class request {
            public static ReservationRequest get() {
                return ReservationRequest.builder()
                    .eventId(R.reservation.event.getId())
                    .quantity(R.reservation.quantity)
                    .build();
            }
        }
        public static class response {
            public static ReservationResponse get() {
                return ReservationResponse.builder()
                    .id(R.reservation.id)
                    .member(R.reservation.memberResponse)
                    .event(R.reservation.eventResponse)
                    .price(R.reservation.price)
                    .quantity(R.reservation.quantity)
                    .reservationDate(R.reservation.reservationDate)
                    .build();

            }
            public static class slice {
                public static ReservationSliceResponse get() {
                    return ReservationSliceResponse.builder()
                        .reservations(List.of(
                            ReservationData.reservation.response.get()))
                        .sliceInfo(CommonData.sliceInfo.get())
                        .build();
                }
            }
        }
    }

}
