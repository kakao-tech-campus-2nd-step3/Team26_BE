package org.ktc2.cokaen.wouldyouin.reservation;

import static java.lang.Math.abs;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;
import java.util.Random;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.ktc2.cokaen.wouldyouin._common.api.EntityGettable;
import org.ktc2.cokaen.wouldyouin.event.persist.Event;
import org.ktc2.cokaen.wouldyouin.global.TestData;
import org.ktc2.cokaen.wouldyouin.member.persist.Member;
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
    private EntityGettable<Long, Member> memberService;
    @Mock
    private EntityGettable<Long, Event> eventService;

    private Long id;

    @BeforeEach
    void setUp() {
        reservationService = new ReservationService(reservationRepository, memberService, eventService);
        id = abs(new Random().nextLong());
    }

    @Test
    @DisplayName("모든 예약 조회 - 성공")
    void getAll() {
        when(reservationRepository.findAll()).thenReturn(List.of());
        reservationService.getAll();
        verify(reservationRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("사용자 id를 통한 모든 예약 조회 - 성공")
    void getAllByMemberId() {
        when(reservationRepository.findByMemberId(id)).thenReturn(List.of());
        reservationService.getAllByMemberId(id);
        verify(reservationRepository, times(1)).findByMemberId(id);
    }

    @Test
    @DisplayName("행사 id를 통한 모든 예약 조회 - 성공")
    void getAllByEventId() {
        when(reservationRepository.findByEventId(id)).thenReturn(List.of());
        reservationService.getAllByEventId(id);
        verify(reservationRepository, times(1)).findByEventId(id);
    }

    @Test
    @DisplayName("예약 id를 통한 모든 예약 조회 - 성공")
    void getById() {
        when(reservationRepository.findById(id)).thenReturn(Optional.of(TestData.validReservation));
        reservationService.getById(id);
        verify(reservationRepository, times(1)).findById(id);
    }

    @Test
    @DisplayName("유효하지 않은 예약 id를 통한 모든 예약 조회 - 실패")
    void getByInvalidId() {
        when(reservationRepository.findById(id)).thenThrow(RuntimeException.class);
        assertThrows(RuntimeException.class, () -> reservationService.getById(id));
    }

    @Test
    @DisplayName("예약 생성 - 성공")
    void create() {
        when(reservationRepository.save(any())).thenReturn(TestData.validReservation);
        reservationService.create(TestData.validReservationRequest);
        verify(reservationRepository, times(1)).save(any());
    }

    @Test
    @DisplayName("예약 삭제 - 성공")
    void delete() {
        when(reservationRepository.findById(id)).thenReturn(Optional.of(TestData.validReservation));
        reservationService.delete(id);
        verify(reservationRepository, times(1)).findById(id);
    }

    @Test
    @DisplayName("예약 삭제 - 실패")
    void deleteByInvalidId() {
        when(reservationRepository.findById(id)).thenThrow(RuntimeException.class);
        assertThrows(RuntimeException.class, () -> reservationService.delete(id));
    }
}