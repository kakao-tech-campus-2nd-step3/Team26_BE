package org.ktc2.cokaen.wouldyouin.event;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;

import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.ktc2.cokaen.wouldyouin._common.exception.UnauthorizedException;
import org.ktc2.cokaen.wouldyouin._common.vo.Area;
import org.ktc2.cokaen.wouldyouin._common.vo.Category;
import org.ktc2.cokaen.wouldyouin._global.testdata.CurationData;
import org.ktc2.cokaen.wouldyouin._global.testdata.CurationData.R.curation1;
import org.ktc2.cokaen.wouldyouin._global.testdata.EventData;
import org.ktc2.cokaen.wouldyouin._global.testdata.EventData.EventSlice;
import org.ktc2.cokaen.wouldyouin._global.testdata.EventData.R.event1;
import org.ktc2.cokaen.wouldyouin._global.testdata.EventData.R.updatedEvent1;
import org.ktc2.cokaen.wouldyouin._global.testdata.ImageData;
import org.ktc2.cokaen.wouldyouin._global.testdata.ImageData.R;
import org.ktc2.cokaen.wouldyouin._global.testdata.ImageData.R.event4;
import org.ktc2.cokaen.wouldyouin._global.testdata.MemberData;
import org.ktc2.cokaen.wouldyouin._global.testdata.MemberData.R.host1;
import org.ktc2.cokaen.wouldyouin.auth.MemberIdentifier;
import org.ktc2.cokaen.wouldyouin.curation.persist.Curation;
import org.ktc2.cokaen.wouldyouin.event.api.dto.EventResponse;
import org.ktc2.cokaen.wouldyouin.event.api.dto.EventSliceResponse;
import org.ktc2.cokaen.wouldyouin.event.api.dto.LocationFilter;
import org.ktc2.cokaen.wouldyouin.event.api.dto.LocationRequest;
import org.ktc2.cokaen.wouldyouin.event.application.EventService;
import org.ktc2.cokaen.wouldyouin.event.persist.Event;
import org.ktc2.cokaen.wouldyouin.event.persist.EventRepository;
import org.ktc2.cokaen.wouldyouin.image.application.EventImageService;
import org.ktc2.cokaen.wouldyouin.image.application.MemberImageService;
import org.ktc2.cokaen.wouldyouin.image.persist.EventImage;
import org.ktc2.cokaen.wouldyouin.member.application.HostService;
import org.ktc2.cokaen.wouldyouin.member.persist.MemberType;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;

@ExtendWith(MockitoExtension.class)
class EventServiceUnitTest {

    private EventService eventService;

    @Mock
    private EventRepository eventRepository;

    @Mock
    private HostService hostService;

    @Mock
    private EventImageService eventImageService;

    @Mock
    private MemberImageService memberImageService;

    @BeforeEach
    void setUp() {
        eventService = new EventService(eventRepository, hostService, eventImageService,
            memberImageService);
    }

    @Test
    @DisplayName("행사 ID를 통해 해당하는 행사를 반환한다.")
    void getById() {
        // given
        given(eventRepository.findById(event1.id)).willReturn(
            Optional.of(EventData.event1.entity.get()));
        EventImage eventImage1 = EventData.event1.entity.get().getImages().get(0);
        EventImage eventImage2 = EventData.event1.entity.get().getImages().get(1);
        EventImage eventImage3 = EventData.event1.entity.get().getImages().get(2);
        given(eventImageService.getImageUrl(eventImage1)).willReturn(R.event1.url);
        given(eventImageService.getImageUrl(eventImage2)).willReturn(R.event2.url);
        given(eventImageService.getImageUrl(eventImage3)).willReturn(R.event3.url);
        // when
        EventResponse response = eventService.getById(event1.id);

        // then
        assertThat(response).isEqualTo(EventData.event1.response.get());
    }

    @Test
    @DisplayName("거리에 따른 이벤트들을 반환한다. ")
    void getAllByFilterOrderByDistanceAsc() {
        // given
        LocationFilter location = new LocationFilter(0.0, 0.0, 10.0, 10.0);
        LocationRequest currentLocation = new LocationRequest(3.0, 2.0);
        given(eventRepository.findAllByFilterOrderByDistance(
            location.getStartLatitude(), location.getStartLongitude(),
            location.getEndLatitude(), location.getEndLongitude(),
            currentLocation.getLatitude(), currentLocation.getLongitude(),
            event1.title, event1.category, event1.area, event1.pageable))
            .willReturn(EventData.EventSlice.get());
        EventImage eventImage1 = EventData.event1.entity.get().getImages().get(0);
        EventImage eventImage2 = EventData.event1.entity.get().getImages().get(1);
        EventImage eventImage3 = EventData.event1.entity.get().getImages().get(2);
        given(eventImageService.getImageUrl(eventImage1)).willReturn(R.event1.url);
        given(eventImageService.getImageUrl(eventImage2)).willReturn(R.event2.url);
        given(eventImageService.getImageUrl(eventImage3)).willReturn(R.event3.url);

        // when
        EventSliceResponse responses = eventService.getAllByFilterOrderByDistanceAsc(
            location, currentLocation, event1.title, event1.category, event1.area, event1.pageable, event1.lastId);

        // then
        assertThat(responses).isEqualTo(EventData.event1.response.slice.get());
    }

    @Test
    @DisplayName("Host ID를 통해 해당하는 모든 이벤트를 반환한다.")
    void getAllByHostIdOrderByCreatedDateDesc() {
        // given
        given(eventRepository.findAllByHostIdOrderByEventIdDesc(host1.id, event1.lastId, event1.pageable))
            .willReturn(EventData.EventSlice.get());
        EventImage eventImage1 = EventData.event1.entity.get().getImages().get(0);
        EventImage eventImage2 = EventData.event1.entity.get().getImages().get(1);
        EventImage eventImage3 = EventData.event1.entity.get().getImages().get(2);
        given(eventImageService.getImageUrl(eventImage1)).willReturn(R.event1.url);
        given(eventImageService.getImageUrl(eventImage2)).willReturn(R.event2.url);
        given(eventImageService.getImageUrl(eventImage3)).willReturn(R.event3.url);

        // when
        EventSliceResponse responses = eventService.getAllByHostIdOrderByCreatedDateDesc(host1.id, event1.pageable, event1.lastId);

        // then
        assertThat(responses).isEqualTo(EventData.event1.response.slice.get());
    }

    @Test
    @DisplayName("모든 이벤트를 반환한다.")
    void getAllByCreatedDateDesc() {
        // given
        given(eventRepository.findAllByEventIdDesc(event1.lastId, event1.pageable))
            .willReturn(EventData.EventSlice.get());
        EventImage eventImage1 = EventData.event1.entity.get().getImages().get(0);
        EventImage eventImage2 = EventData.event1.entity.get().getImages().get(1);
        EventImage eventImage3 = EventData.event1.entity.get().getImages().get(2);
        given(eventImageService.getImageUrl(eventImage1)).willReturn(R.event1.url);
        given(eventImageService.getImageUrl(eventImage2)).willReturn(R.event2.url);
        given(eventImageService.getImageUrl(eventImage3)).willReturn(R.event3.url);

        // when
        EventSliceResponse responses = eventService.getAllByCreatedDateDesc(event1.pageable, event1.lastId);

        // then
        assertThat(responses).isEqualTo(EventData.event1.response.slice.get());
    }
    @Test
    @DisplayName("EventCreateRequest를 통해 이벤트를 생성한다.")
    void create() {
        // given
        given(hostService.getByIdOrThrow(host1.id)).willReturn(MemberData.host1.entity.get());
        given(eventImageService.getById(R.event1.id)).willReturn(ImageData.event1.entity.get());
        given(eventImageService.getById(R.event2.id)).willReturn(ImageData.event2.entity.get());
        given(eventImageService.getById(R.event3.id)).willReturn(ImageData.event3.entity.get());
        EventImage eventImage1 = EventData.event1.entity.get().getImages().get(0);
        EventImage eventImage2 = EventData.event1.entity.get().getImages().get(1);
        EventImage eventImage3 = EventData.event1.entity.get().getImages().get(2);
        given(eventImageService.getImageUrl(eventImage1)).willReturn(R.event1.url);
        given(eventImageService.getImageUrl(eventImage2)).willReturn(R.event2.url);
        given(eventImageService.getImageUrl(eventImage3)).willReturn(R.event3.url);
        given(eventRepository.save(any(Event.class))).willReturn(EventData.event1.entity.get());

        // when
        EventResponse response = eventService.create(host1.memberIdentifier, EventData.event1.request.create.get());

        // then
        assertThat(response).isEqualTo(EventData.event1.response.get());
    }

    @Test
    @DisplayName("EventEditRequest를 통해 이벤트를 수정한다.")
    void update1() {
        // given
        given(eventRepository.findById(event1.id)).willReturn(Optional.of(EventData.event1.entity.get()));
        given(eventImageService.getById(R.event4.id)).willReturn(ImageData.event4.entity.get());
        given(eventImageService.getById(R.event5.id)).willReturn(ImageData.event5.entity.get());
        given(eventImageService.createThumbnail(event4.name)).willReturn(updatedEvent1.thumbnailUrl);
        EventImage eventImage4 = EventData.updatedEvent1.entity.get().getImages().get(0);
        EventImage eventImage5 = EventData.updatedEvent1.entity.get().getImages().get(1);
        given(eventImageService.getImageUrl(eventImage4)).willReturn(R.event4.url);
        given(eventImageService.getImageUrl(eventImage5)).willReturn(R.event5.url);

        // when
        EventResponse response = eventService.update(host1.memberIdentifier, event1.id,
            EventData.event1.request.edit1.get());

        // then
        then(eventImageService).should(times(3)).deleteImage(any(MemberIdentifier.class), any(Long.class));
        assertThat(response).isEqualTo(EventData.updatedEvent1.response.get());
    }

    @Test
    @DisplayName("Host ID가 다르면 큐레이션을 수정할 수 없다.")
    void update2() {
        // given
        MemberIdentifier differentMember = new MemberIdentifier(100L, MemberType.host);
        given(eventRepository.findById(event1.id)).willReturn(Optional.of(EventData.event1.entity.get()));

        // when, then
        UnauthorizedException exception = assertThrows(
            UnauthorizedException.class, () -> eventService.update(differentMember, event1.id, EventData.event1.request.edit1.get()));
        assertThat(exception.getMessage()).isEqualTo("해당 이벤트에 접근할 권한이 없습니다.");
    }

    @Test
    @DisplayName("이벤트 ID를 통해 해당하는 이벤트를 삭제한다.")
    void delete() {
        // given
        given(eventRepository.findById(event1.id)).willReturn(Optional.of(EventData.event1.entity.get()));

        // when
        eventService.delete(host1.memberIdentifier, event1.id);

        // then
        then(eventImageService).should(times(3)).deleteImage(any(MemberIdentifier.class), any(Long.class));
        then(eventRepository).should(times(1)).deleteById(event1.id);
    }

    @Test
    @DisplayName("Host ID가 다르면 큐레이션을 삭제할 수 없다.")
    void deleteByInvalidId() {
        // given
        MemberIdentifier differentMember = new MemberIdentifier(100L, MemberType.host);
        given(eventRepository.findById(event1.id)).willReturn(Optional.of(EventData.event1.entity.get()));

        // when, then
        UnauthorizedException exception = assertThrows(
            UnauthorizedException.class, () -> eventService.delete(differentMember, event1.id));
        assertThat(exception.getMessage()).isEqualTo("해당 이벤트에 접근할 권한이 없습니다.");
    }
}