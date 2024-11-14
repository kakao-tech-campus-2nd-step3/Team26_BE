package org.ktc2.cokaen.wouldyouin.event.application;

import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.ktc2.cokaen.wouldyouin._common.exception.EntityNotFoundException;
import org.ktc2.cokaen.wouldyouin._common.exception.NoLeftSeatException;
import org.ktc2.cokaen.wouldyouin._common.exception.UnauthorizedException;
import org.ktc2.cokaen.wouldyouin._common.vo.Area;
import org.ktc2.cokaen.wouldyouin._common.vo.Category;
import org.ktc2.cokaen.wouldyouin.auth.MemberIdentifier;
import org.ktc2.cokaen.wouldyouin.event.api.dto.EventCreateRequest;
import org.ktc2.cokaen.wouldyouin.event.api.dto.EventEditRequest;
import org.ktc2.cokaen.wouldyouin.event.api.dto.EventResponse;
import org.ktc2.cokaen.wouldyouin.event.api.dto.EventSliceResponse;
import org.ktc2.cokaen.wouldyouin.event.api.dto.LocationFilter;
import org.ktc2.cokaen.wouldyouin.event.api.dto.LocationRequest;
import org.ktc2.cokaen.wouldyouin.event.persist.Event;
import org.ktc2.cokaen.wouldyouin.event.persist.EventRepository;
import org.ktc2.cokaen.wouldyouin.image.application.EventImageService;
import org.ktc2.cokaen.wouldyouin.image.application.MemberImageService;
import org.ktc2.cokaen.wouldyouin.image.persist.EventImage;
import org.ktc2.cokaen.wouldyouin.member.application.HostService;
import org.ktc2.cokaen.wouldyouin.member.persist.Host;
import org.ktc2.cokaen.wouldyouin.member.persist.MemberType;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class EventService {

    private final EventRepository eventRepository;
    private final HostService hostService;
    private final EventImageService eventImageService;
    private final MemberImageService memberImageService;

    @Transactional
    public Event getByIdOrThrow(Long id) throws EntityNotFoundException {
        return eventRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("해당하는 이벤트를 찾을 수 없습니다."));
    }

    @Transactional(readOnly = true)
    public EventResponse getById(Long id) {
        Event event = getByIdOrThrow(id);
        return EventResponse.from(event, getImageUrl(event));
    }

    @Transactional(readOnly = true)
    public EventSliceResponse getAllByFilterOrderByDistanceAsc(LocationFilter location, LocationRequest currentLocation, String title,
        Category category, Area area, Pageable pageable, Long beforeLastId) {
        Slice<Event> events = eventRepository.findAllByFilterOrderByDistance(
            location.getStartLatitude(), location.getStartLongitude(), location.getEndLatitude(),
            location.getEndLongitude(), currentLocation.getLatitude(), currentLocation.getLongitude(),
            title, category, area, pageable
        );
        Long newLastId = getLastId(events, beforeLastId);
        List<EventResponse> responses = events.stream().map(this::getEventResponse).toList();
        return EventSliceResponse.from(responses, events.getSize(), newLastId);
    }

    @Transactional(readOnly = true)
    public EventSliceResponse getAllByHostIdOrderByCreatedDateDesc(Long hostId, Pageable pageable, Long beforeLastId) {
        Slice<Event> events = eventRepository.findAllByHostIdOrderByEventIdDesc(hostId, beforeLastId, pageable);
        Long newLastId = getLastId(events, beforeLastId);
        List<EventResponse> responses = events.stream().map(this::getEventResponse).toList();
        return EventSliceResponse.from(responses, events.getSize(), newLastId);
    }

    @Transactional(readOnly = true)
    public EventSliceResponse getAllByCreatedDateDesc(Pageable pageable, Long beforeLastId) {
        Slice<Event> events = eventRepository.findAllByEventIdDesc(beforeLastId, pageable);
        Long newLastId = getLastId(events, beforeLastId);
        List<EventResponse> responses = events.stream().map(this::getEventResponse).toList();
        return EventSliceResponse.from(responses, events.getSize(), newLastId);
    }

    @Transactional
    public EventResponse create(MemberIdentifier identifier, EventCreateRequest eventCreateRequest) {
        Host host = hostService.getByIdOrThrow(identifier.id());
        List<EventImage> images = eventCreateRequest.getImageIds().stream()
            .map(eventImageService::getById).toList();
        Event event = eventRepository.save(eventCreateRequest.toEntity(host, images, getThumbnailUrl(images)));
        images.forEach(image -> eventImageService.setEvent(image, event));
        return getEventResponse(event);
    }

    private String getThumbnailUrl(List<EventImage> images) {
        return Optional.ofNullable(images)
            .map(List::getFirst)
            .map(EventImage::getName)
            .map(eventImageService::createThumbnail)
            .orElse("");
    }

    @Transactional
    public EventResponse update(MemberIdentifier identifier, Long eventId, EventEditRequest eventEditRequest) {
        Event event = getByIdOrThrow(eventId);
        validateHostId(identifier, event);
        event.getImages().forEach(image -> eventImageService.deleteImage(identifier, image.getId()));
        List<EventImage> images = eventEditRequest.getImageIds().stream()
            .map(eventImageService::getById).toList();
        event.updateFrom(eventEditRequest, images, getThumbnailUrl(images));
        images.forEach(image -> eventImageService.setEvent(image, event));
        return getEventResponse(event);
    }

    @Transactional
    public void delete(MemberIdentifier identifier, Long eventId) {
        validateHostId(identifier, getByIdOrThrow(eventId));
        eventImageService.deleteImage(identifier, eventId);
        eventRepository.deleteById(eventId);
    }

    @Transactional
    public void decreaseLeftSeat(Long eventId, Integer count) {
        Event event = getByIdOrThrow(eventId);
        if (event.getLeftSeat() < count) {
            throw new NoLeftSeatException("남은 좌석이 부족합니다.");
        }
        event.decreaseLeftSeat(count);
    }

    public static Long getLastId(Slice<Event> events, Long oldLastId) {
        if (events.hasContent()) {
            return events.getContent().getLast().getId();
        }
        return oldLastId;
    }

    public void validateHostId(MemberIdentifier identifier, Event event) {
        if (!identifier.type().equals(MemberType.admin) && !identifier.id().equals(event.getHost().getId())) {
            throw new UnauthorizedException("해당 이벤트에 접근할 권한이 없습니다.");
        }
    }

    private EventResponse getEventResponse(Event event) {
        return EventResponse.from(event, getImageUrl(event));
    }

    private List<String> getImageUrl(Event event) {
        return event.getImages().stream()
            .map(eventImageService::getImageUrl)
            .toList();
    }
}