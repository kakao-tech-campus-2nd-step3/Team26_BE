package org.ktc2.cokaen.wouldyouin.event;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.times;

import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.ktc2.cokaen.wouldyouin.Image.application.EventImageService;
import org.ktc2.cokaen.wouldyouin._common.vo.Area;
import org.ktc2.cokaen.wouldyouin._common.vo.Category;
import org.ktc2.cokaen.wouldyouin._global.TestData.EventDomain;
import org.ktc2.cokaen.wouldyouin.event.api.dto.LocationFilter;
import org.ktc2.cokaen.wouldyouin.event.api.dto.EventCreateRequest;
import org.ktc2.cokaen.wouldyouin.event.api.dto.EventEditRequest;
import org.ktc2.cokaen.wouldyouin.event.api.dto.UserLocation;
import org.ktc2.cokaen.wouldyouin.event.application.EventService;
import org.ktc2.cokaen.wouldyouin.event.persist.Event;
import org.ktc2.cokaen.wouldyouin.event.persist.EventRepository;
import org.ktc2.cokaen.wouldyouin.member.application.HostService;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.SliceImpl;

@ExtendWith(MockitoExtension.class)
class EventServiceUnitTest {

    private EventService eventService;

    @Mock
    private EventRepository eventRepository;

    @Mock
    private HostService hostService;

    @Mock
    private EventImageService eventImageService;

    private Event validEvent;

    @BeforeEach
    void setUp() {
        eventService = new EventService(eventRepository, hostService, eventImageService);
        validEvent = EventDomain.createValidEvent();
    }

    @Test
    @DisplayName("모든 행사 조회 - 성공")
    void getAllByFilterOrderByDistanceAsc() {
        // given
        LocationFilter location = new LocationFilter();
        UserLocation currentLocation = new UserLocation(3.0, 2.0);
        String title = "testTitle";
        Category category = Category.공예;
        Area area = Area.광주;
        int pageNumber = 1;
        int pageSize = 10;
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Long lastId = 1L;
        given(eventRepository.findAllByFilterOrderByDistance(location.getStartLatitude(),
            location.getStartLongitude(),
            location.getEndLatitude(), location.getEndLongitude(), currentLocation.getLatitude(),
            currentLocation.getLongitude(), title, category, area, pageable)).willReturn(
            new SliceImpl<>(List.of()));

        // when
        eventService.getAllByFilterOrderByDistanceAsc(location, currentLocation, title, category,
            area, pageable, lastId);

        // then
        then(eventRepository).should(times(1))
            .findAllByFilterOrderByDistance(any(Double.class), any(Double.class), any(Double.class),
                any(Double.class), any(Double.class), any(Double.class),
                any(String.class), any(Category.class), any(Area.class), any(Pageable.class));
    }

    @Test
    @DisplayName("주최자 id를 통한 모든 행사 조회 - 성공")
    void getAllByHostIdOrderByCreatedDateDesc() {
        // given
        Long hostId = validEvent.getHost().getId();
        int pageNumber = 1;
        int pageSize = 10;
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Long lastId = 1L;
        given(eventRepository.findAllByHostIdOrderByEventIdDesc(hostId, lastId, pageable))
            .willReturn(new SliceImpl<>(List.of()));

        // when
        eventService.getAllByHostIdOrderByCreatedDateDesc(hostId, pageable, lastId);

        // then
        then(eventRepository).should(times(1)).
            findAllByHostIdOrderByEventIdDesc(any(Long.class), any(Long.class),
                any(Pageable.class));
    }

    @Test
    @DisplayName("행사 id를 통한 행사 조회 - 성공")
    void getById() {
        // given
        given(eventRepository.findById(validEvent.getId())).willReturn(Optional.of(validEvent));

        // when
        eventService.getById(validEvent.getId());

        // then
        then(eventRepository).should(times(1)).findById(validEvent.getId());
    }

    @Test
    @DisplayName("유효하지 않은 행사 id를 통한 행사 조회 - 실패")
    void getByInvalidId() {
        // given
        Long eventId = validEvent.getId();
        given(eventRepository.findById(eventId)).willThrow(RuntimeException.class);

        // when & then
        assertThrows(RuntimeException.class, () -> eventService.getById(eventId));
    }

    @Test
    @DisplayName("행사 생성 - 성공")
    void create() {
        // given
        Long hostId = validEvent.getHost().getId();
        EventCreateRequest validEventCreateRequest = EventDomain.createValidEventCreateRequest();
        given(eventRepository.save(any())).willReturn(validEvent);

        // when
        eventService.create(hostId, validEventCreateRequest);

        // then
        then(eventRepository).should(times(1)).save(any(Event.class));
    }

//    @Test
    @DisplayName("행사 id를 통한 행사 수정 - 성공")
    void update() {
        // given
        Long eventId = validEvent.getId();
        Long hostId = validEvent.getHost().getId();
        EventEditRequest validEventEditRequest = EventDomain.createValidEventEditRequest();
        given(eventRepository.findById(eventId)).willReturn(Optional.of(validEvent));

        // when
        eventService.update(hostId, eventId, validEventEditRequest);

        // then
        then(eventRepository).should(times(1)).findById(eventId);
        assertAll(
            () -> assertEquals(validEvent.getTitle(), validEventEditRequest.getTitle()),
            () -> assertEquals(validEvent.getContent(), validEventEditRequest.getContent()),
            () -> assertEquals(validEvent.getArea(), validEventEditRequest.getArea()),
            () -> assertEquals(validEvent.getLocation(), validEventEditRequest.getLocation()),
            () -> assertEquals(validEvent.getStartTime(), validEventEditRequest.getStartTime()),
            () -> assertEquals(validEvent.getEndTime(), validEventEditRequest.getEndTime()),
            () -> assertEquals(validEvent.getPrice(), validEventEditRequest.getPrice()),
            () -> assertEquals(validEvent.getTotalSeat(), validEventEditRequest.getTotalSeat()),
            () -> assertEquals(validEvent.getCategory(), validEventEditRequest.getCategory())
        );
    }

    @Test
    @DisplayName("유효하지 않은 행사 id를 통한 행사 수정 - 실패")
    void updateByInvalidId() {
        // given
        EventEditRequest request = EventDomain.createValidEventEditRequest();
        Long invalidHostId = 999L;
        given(eventRepository.findById(invalidHostId)).willThrow(RuntimeException.class);

        // when & then
        assertThrows(RuntimeException.class,
            () -> eventService.update(invalidHostId, validEvent.getId(),
                EventDomain.createValidEventEditRequest()));
    }

    @Test
    @DisplayName("행사 삭제 - 성공")
    void delete() {
        // given
        Long eventId = validEvent.getId();
        Long hostId = validEvent.getHost().getId();

        // when
        given(eventRepository.findById(eventId)).willReturn(Optional.of(validEvent));
        willDoNothing().given(eventRepository).deleteById(eventId);
        eventService.delete(hostId, eventId);

        // then
        then(eventRepository).should(times(1)).findById(eventId);
        then(eventRepository).should(times(1)).deleteById(eventId);
    }

    @Test
    @DisplayName("유효하지 않은 행사 id를 통한 행사 삭제 - 실패")
    void deleteByInvalidId() {
        // given
        Long eventId = validEvent.getId();
        Long hostId = validEvent.getHost().getId();
        given(eventRepository.findById(eventId)).willThrow(RuntimeException.class);

        // when & then
        assertThrows(RuntimeException.class, () -> eventService.delete(hostId, eventId));
    }
}