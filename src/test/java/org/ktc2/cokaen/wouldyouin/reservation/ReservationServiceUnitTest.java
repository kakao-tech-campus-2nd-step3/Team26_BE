package org.ktc2.cokaen.wouldyouin.reservation;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;

import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.ktc2.cokaen.wouldyouin._common.exception.ReservationNotFoundForReviewException;
import org.ktc2.cokaen.wouldyouin._common.exception.UnauthorizedException;
import org.ktc2.cokaen.wouldyouin._global.testdata.MemberData.R.normal1;
import org.ktc2.cokaen.wouldyouin._global.testdata.ReservationData;
import org.ktc2.cokaen.wouldyouin._global.testdata.ReservationData.R;
import org.ktc2.cokaen.wouldyouin._global.testdata.ReservationData.R.reservation1;
import org.ktc2.cokaen.wouldyouin.auth.MemberIdentifier;
import org.ktc2.cokaen.wouldyouin.event.application.EventService;
import org.ktc2.cokaen.wouldyouin.member.application.MemberService;
import org.ktc2.cokaen.wouldyouin.member.persist.MemberType;
import org.ktc2.cokaen.wouldyouin.payment.application.PaymentService;
import org.ktc2.cokaen.wouldyouin.reservation.api.dto.KakaoPayReservationResponse;
import org.ktc2.cokaen.wouldyouin.reservation.api.dto.ReservationResponse;
import org.ktc2.cokaen.wouldyouin.reservation.api.dto.ReservationSliceResponse;
import org.ktc2.cokaen.wouldyouin.reservation.application.ReservationService;
import org.ktc2.cokaen.wouldyouin.reservation.persist.ReservationRepository;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith({MockitoExtension.class})
class ReservationServiceUnitTest {

    private ReservationService reservationService;

    @Mock
    private ReservationRepository reservationRepository;
    @Mock
    private MemberService memberService;
    @Mock
    private EventService eventService;
    @Mock
    private PaymentService paymentService;

    @BeforeEach
    void setUp() {
        reservationService = new ReservationService(reservationRepository, paymentService, memberService, eventService);
    }

    @Test
    @DisplayName("예약 ID를 통한 해당 예약을 반환한다.")
    void getById() {
        // given
        given(reservationRepository.findById(reservation1.id)).willReturn(Optional.of(ReservationData.reservation1.entity.get()));

        // when
        ReservationResponse response = reservationService.getById(reservation1.id);

        // then
        assertThat(response).isEqualTo(ReservationResponse.from(ReservationData.reservation1.entity.get()));
    }

    @Test
    @DisplayName("멤버 ID를 통해 자신의 모든 예약을 반환한다.")
    void getAllByMemberId() {
        // given
        given(reservationRepository.findByMemberIdOrderByReservationIdDesc(normal1.id, R.lastId, ReservationData.R.pageable))
            .willReturn(ReservationData.ReservationSlice.get());

        // when
        ReservationSliceResponse response =
            reservationService.getAllByMemberId(normal1.memberIdentifier, R.pageable, R.lastId);

        // then
        assertThat(response).isEqualTo(ReservationData.sliceResponse.get());
    }

    @Test
    @DisplayName("이벤트 ID를 통해 해당 행사의 모든 예약을 반환한다.")
    void getAllByEventId() {
        // given
        given(eventService.getByIdOrThrow(R.reservation1._Relation.event().getId())).willReturn(R.reservation1._Relation.event());
        given(reservationRepository.findByEventIdOrderByReservationIdDesc(R.reservation1._Relation.event().getId(), R.lastId, R.pageable))
            .willReturn(ReservationData.ReservationSlice.get());

        // when
        ReservationSliceResponse response =
            reservationService.getAllByEventId(normal1.memberIdentifier, R.reservation1._Relation.event().getId(), R.pageable, R.lastId);

        // then
        assertThat(response).isEqualTo(ReservationData.sliceResponse.get());
    }

    @Test
    @DisplayName("예약 생성 DTO를 통해 결제를 진행하고 예약을 생성한다.")
    void create() {
        // given
        given(reservationRepository.save(any())).willReturn(ReservationData.reservation1.entity.get());
        given(eventService.getByIdOrThrow(R.reservation1._Relation.event().getId())).willReturn(R.reservation1._Relation.event());
        given(reservationRepository.save(any())).willReturn(ReservationData.reservation1.entity.get());
        given(paymentService.createPayment(any())).willReturn(ReservationData.reservation1.kakaoPayResponse.get());

        // when
        KakaoPayReservationResponse response =
            reservationService.create(normal1.memberIdentifier, ReservationData.reservation1.request.get());

        // then
        assertThat(response).isEqualTo(ReservationData.reservation1.kakaoPayReservationResponse.get());
    }

    @Test
    @DisplayName("예약 생성 DTO를 통해 예약을 생성한다.")
    void createTest() {
        // given
        given(reservationRepository.save(any())).willReturn(ReservationData.reservation1.entity.get());
        given(eventService.getByIdOrThrow(R.reservation1._Relation.event().getId())).willReturn(R.reservation1._Relation.event());
        given(reservationRepository.save(any())).willReturn(ReservationData.reservation1.entity.get());

        // when
        ReservationResponse response =
            reservationService.createTest(normal1.memberIdentifier, ReservationData.reservation1.request.get());

        // then
        assertThat(response).isEqualTo(ReservationData.reservation1.response.get());
    }

    @Test
    @DisplayName("예약 ID를 통해 해당 예약을 삭제한다.")
    void delete1() {
        // given
        given(reservationRepository.findById(reservation1.id)).willReturn(Optional.of(ReservationData.reservation1.entity.get()));

        // when
        reservationService.delete(normal1.memberIdentifier, reservation1.id);

        // then
        then(reservationRepository).should(times(1)).deleteById(reservation1.id);
    }

    @Test
    @DisplayName("멤버 ID와 예약의 멤버 ID가 일치하지 않으면 예약을 삭제할 수 없다.")
    void delete2() {
        // given
        MemberIdentifier anotherMember = new MemberIdentifier(100L, MemberType.normal);
        given(reservationRepository.findById(reservation1.id)).willReturn(Optional.of(ReservationData.reservation1.entity.get()));

        // when
        UnauthorizedException exception =
            assertThrows(UnauthorizedException.class, () -> reservationService.delete(anotherMember, reservation1.id));

        // then
        then(reservationRepository).should(times(0)).deleteById(reservation1.id);
        assertThat(exception.getMessage()).isEqualTo("사용자 ID가 예약한 사용자 ID와 일치하지 않습니다.");
    }

    @Test
    @DisplayName("사용자는 해당 이벤트에 대한 예약이 없으면 리뷰를 작성할 수 없다.")
    void validateByMemberIdAndEventId() {
        // given
        given(reservationRepository.findByMemberIdAndEventId(normal1.id, R.reservation1._Relation.event().getId())).willReturn(List.of());

        // when
        ReservationNotFoundForReviewException exception =
            assertThrows(ReservationNotFoundForReviewException.class,
                () -> reservationService.validateByMemberIdAndEventId(normal1.id, R.reservation1._Relation.event().getId()));

        // then
        assertThat(exception.getMessage()).isEqualTo("해당 이벤트에 대한 리뷰를 작성할 수 없습니다.");
    }
}