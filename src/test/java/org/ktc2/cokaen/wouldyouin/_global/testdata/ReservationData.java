package org.ktc2.cokaen.wouldyouin._global.testdata;

import java.time.LocalDateTime;
import java.util.List;
import org.ktc2.cokaen.wouldyouin._global.testdata.MemberData.response.reservation1Member1;
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
        public static class reservation1 {
            public static class _Relation {
                public static Member member() {
                    return MemberData.normal1.entity.get();
                }
                public static Event event() {
                    return EventData.event1.entity.get();
                }
            }
            public static final Long id = 1L;
            public static final Integer price = _Relation.event().getPrice();
            public static final Integer quantity = 2;
            public static final LocalDateTime reservationDate = LocalDateTime.of(2024, 3, 23, 0, 0);

            public static final ReservationMemberResponse memberResponse = reservation1Member1.get();
            public static final ReservationEventResponse eventResponse = EventData.response.reservationEvent.createValidReservationEventResponse();
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
                ReflectionTestUtils.setField(ret, "reservationDate", R.reservation1.reservationDate);
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
    }
    public static class sliceResponse {
        public static ReservationSliceResponse get() {
            return ReservationSliceResponse.builder()
                .reservations(List.of(
                    ReservationData.reservation1.response.get()))
                .sliceInfo(CommonData.sliceInfo.get())
                .build();
        }
    }

}
