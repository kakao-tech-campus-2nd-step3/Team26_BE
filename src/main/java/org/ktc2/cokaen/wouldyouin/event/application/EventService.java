package org.ktc2.cokaen.wouldyouin.event.application;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.ktc2.cokaen.wouldyouin.Image.application.EventImageService;
import org.ktc2.cokaen.wouldyouin.Image.persist.EventImage;
import org.ktc2.cokaen.wouldyouin._common.exception.EntityNotFoundException;
import org.ktc2.cokaen.wouldyouin._common.exception.NoLeftSeatException;
import org.ktc2.cokaen.wouldyouin._common.exception.UnauthorizedException;
import org.ktc2.cokaen.wouldyouin._common.vo.Area;
import org.ktc2.cokaen.wouldyouin._common.vo.Category;
import org.ktc2.cokaen.wouldyouin.event.api.dto.UserLocation;
import org.ktc2.cokaen.wouldyouin.event.api.dto.LocationFilter;
import org.ktc2.cokaen.wouldyouin.event.api.dto.EventCreateRequest;
import org.ktc2.cokaen.wouldyouin.event.api.dto.EventEditRequest;
import org.ktc2.cokaen.wouldyouin.event.api.dto.EventResponse;
import org.ktc2.cokaen.wouldyouin.event.api.dto.EventSliceResponse;
import org.ktc2.cokaen.wouldyouin.event.persist.Event;
import org.ktc2.cokaen.wouldyouin.event.persist.EventRepository;
import org.ktc2.cokaen.wouldyouin.member.application.HostService;
import org.ktc2.cokaen.wouldyouin.member.persist.Host;
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

    @Transactional
    public Event getByIdOrThrow(Long id) throws EntityNotFoundException {
        return eventRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("해당하는 이벤트를 찾을 수 없습니다."));
    }

    @Transactional(readOnly = true)
    public EventResponse getById(Long id) {
        return EventResponse.from(getByIdOrThrow(id));
    }

    @Transactional(readOnly = true)
    public EventSliceResponse getAllByFilterOrderByDistanceAsc(LocationFilter location, UserLocation currentLocation, String title,
        Category category, Area area, Pageable pageable, Long beforeLastId) {
        Slice<Event> events = eventRepository.findAllByFilterOrderByDistance(
            location.getStartLatitude(), location.getStartLongitude(), location.getEndLatitude(),
            location.getEndLongitude(), currentLocation.getLatitude(), currentLocation.getLongitude(),
            title, category, area, pageable
        );
        Long newLastId = getLastId(events, beforeLastId);
        return EventSliceResponse.from(events, events.getSize(), newLastId);
    }

    @Transactional(readOnly = true)
    public EventSliceResponse getAllByHostIdOrderByCreatedDateDesc(Long hostId, Pageable pageable, Long beforeLastId) {
        Slice<Event> events = eventRepository.findAllByHostIdOrderByEventIdDesc(hostId, beforeLastId, pageable);
        Long newLastId = getLastId(events, beforeLastId);
        return EventSliceResponse.from(events, events.getSize(), newLastId);
    }

    @Transactional
    public EventResponse create(Long hostId, EventCreateRequest eventCreateRequest) {
        Host host = hostService.getByIdOrThrow(hostId);
        List<EventImage> images = eventCreateRequest.getImageIds().stream()
            .map(eventImageService::getById).toList();
        Event event = eventRepository.save(eventCreateRequest.toEntity(host, images));
        images.forEach(image -> eventImageService.setEvent(image, event));
        return EventResponse.from(event);
    }

    @Transactional
    public EventResponse update(Long hostId, Long eventId, EventEditRequest eventEditRequest) {
        Event event = getByIdOrThrow(eventId);
        validateHostId(hostId, event);
        event.getImages().forEach(image -> eventImageService.deleteImage(image.getId()));
        List<EventImage> images = eventEditRequest.getImageIds().stream()
            .map(eventImageService::getById).toList();
        event.updateFrom(eventEditRequest, images);
        images.forEach(image -> eventImageService.setEvent(image, event));
        return EventResponse.from(event);
    }

    @Transactional
    public void delete(Long hostId, Long eventId) {
        validateHostId(hostId, getByIdOrThrow(eventId));
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

    private static Long getLastId(Slice<Event> events, Long oldLastId) {
        if (events.hasContent()) {
            return events.getContent().getLast().getId();
        }
        return oldLastId;
    }

    public void validateHostId(Long hostId, Event event) {
        if (!hostId.equals(event.getHost().getId())) {
            throw new UnauthorizedException("호스트 ID가 행사의 호스트 ID와 일치하지 않습니다.");
        }
    }
}