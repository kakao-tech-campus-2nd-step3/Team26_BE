package org.ktc2.cokaen.wouldyouin.event;

import static java.lang.Math.abs;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
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
import org.ktc2.cokaen.wouldyouin.event.application.EventService;
import org.ktc2.cokaen.wouldyouin.event.persist.EventRepository;
import org.ktc2.cokaen.wouldyouin.global.TestData;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class EventServiceUnitTest {

    private EventService eventService;

    @Mock
    private EventRepository eventRepository;

    private long id;

    @BeforeEach
    void setUp() {
        eventService = new EventService(eventRepository);
        id = abs(new Random().nextLong());
    }

    @Test
    @DisplayName("모든 행사 조회 - 성공")
    void getAll() {
        when(eventRepository.findAll()).thenReturn(List.of());
        eventService.getAll();
        verify(eventRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("주최자 id를 통한 모든 행사 조회 - 성공")
    void getAllByHostId() {
        when(eventRepository.findByHostId(id)).thenReturn(List.of());
        eventService.getAllByHostId(id);
        verify(eventRepository, times(1)).findByHostId(id);
    }

    @Test
    @DisplayName("행사 id를 통한 행사 조회 - 성공")
    void getById() {
        when(eventRepository.findById(id)).thenReturn(Optional.of(TestData.validEvent));
        eventService.getById(id);
        verify(eventRepository, times(1)).findById(id);
    }

    @Test
    @DisplayName("유효하지 않은 행사 id를 통한 행사 조회 - 실패")
    void getByInvalidId() {
        when(eventRepository.findById(id)).thenThrow(RuntimeException.class);
        assertThrows(RuntimeException.class, () -> eventService.getById(id));
    }

    @Test
    @DisplayName("행사 생성 - 성공")
    void create() {
        when(eventRepository.save(any())).thenReturn(TestData.validEvent);
        eventService.create(TestData.validEventRequest);
        verify(eventRepository, times(1)).save(any());
    }

    @Test
    @DisplayName("행사 id를 통한 행사 수정 - 성공")
    void update() {
        var validEvent = TestData.validEvent;
        System.out.println(validEvent.getId());
        when(eventRepository.findById(id)).thenReturn(Optional.of(validEvent));
        eventService.update(id, TestData.validEventRequestToModify);
        verify(eventRepository, times(1)).findById(id);
        assertAll(
            () -> assertEquals(validEvent.getHostId(), TestData.validEventRequestToModify.getHostId()),
            () -> assertEquals(validEvent.getTitle(), TestData.validEventRequestToModify.getTitle()),
            () -> assertEquals(validEvent.getContent(), TestData.validEventRequestToModify.getContent()),
            () -> assertEquals(validEvent.getArea(), TestData.validEventRequestToModify.getArea()),
            () -> assertEquals(validEvent.getLocation(), TestData.validEventRequestToModify.getLocation()),
            () -> assertEquals(validEvent.getStartTime(), TestData.validEventRequestToModify.getStartTime()),
            () -> assertEquals(validEvent.getEndTime(), TestData.validEventRequestToModify.getEndTime()),
            () -> assertEquals(validEvent.getPrice(), TestData.validEventRequestToModify.getPrice()),
            () -> assertEquals(validEvent.getTotalSeat(), TestData.validEventRequestToModify.getTotalSeat()),
            () -> assertEquals(validEvent.getLeftSeat(), TestData.validEventRequestToModify.getLeftSeat()),
            () -> assertEquals(validEvent.getCategory(), TestData.validEventRequestToModify.getCategory())
        );
    }

    @Test
    @DisplayName("유효하지 않은 행사 id를 통한 행사 수정 - 실패")
    void updateByInvalidId() {
        when(eventRepository.findById(id)).thenThrow(RuntimeException.class);
        assertThrows(RuntimeException.class, () -> eventService.update(id, TestData.validEventRequest));
    }

    @Test
    @DisplayName("행사 삭제 - 성공")
    void delete() {
        when(eventRepository.findById(id)).thenReturn(Optional.of(TestData.validEvent));
        eventService.delete(id);
        verify(eventRepository, times(1)).findById(id);
    }

    @Test
    @DisplayName("유효하지 않은 행사 id를 통한 행사 삭제 - 실패")
    void deleteByInvalidId() {
        when(eventRepository.findById(id)).thenThrow(RuntimeException.class);
        assertThrows(RuntimeException.class, () -> eventService.delete(id));
    }
}