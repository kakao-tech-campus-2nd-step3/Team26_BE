package org.ktc2.cokaen.wouldyouin.event.application;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.ktc2.cokaen.wouldyouin.Image.application.EventImageService;
import org.ktc2.cokaen.wouldyouin._common.exception.EntityNotFoundException;
import org.ktc2.cokaen.wouldyouin._common.exception.UnauthorizedException;
import org.ktc2.cokaen.wouldyouin._common.vo.Area;
import org.ktc2.cokaen.wouldyouin._common.vo.Category;
import org.ktc2.cokaen.wouldyouin._common.vo.Location;
import org.ktc2.cokaen.wouldyouin.curation.api.dto.LocationFilter;
import org.ktc2.cokaen.wouldyouin.event.api.dto.EventCreateRequest;
import org.ktc2.cokaen.wouldyouin.event.api.dto.EventEditRequest;
import org.ktc2.cokaen.wouldyouin.event.api.dto.EventResponse;
import org.ktc2.cokaen.wouldyouin.event.api.dto.EventSliceResponse;
import org.ktc2.cokaen.wouldyouin.event.persist.Event;
import org.ktc2.cokaen.wouldyouin.event.persist.EventRepository;
import org.ktc2.cokaen.wouldyouin.member.application.HostService;
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

    @Transactional(readOnly = true)
    public EventSliceResponse getAllByFilterOrderByDistanceAsc(LocationFilter location, Location currentLocation,
        Category category, Area area, Pageable pageable, Long lastId) {
        return getEventSliceResponse(
            eventRepository.findAllByFilterOrderByDistance(location.getStartLatitude(), location.getStartLongitude(),
                location.getEndLatitude(), location.getEndLongitude(), currentLocation.getLatitude(), currentLocation.getLongitude(),
                category, area, pageable), lastId);
    }

    @Transactional(readOnly = true)
    public EventSliceResponse getAllByHostIdOrderByCreatedDateDesc(Long hostId, Pageable pageable, Long lastId) {
        return getEventSliceResponse(
            eventRepository.findAllByHostIdOrderByEventIdDesc(hostId, lastId, pageable), lastId);
    }

    private EventSliceResponse getEventSliceResponse(Slice<Event> eventSlice, Long lastId) {
        List<EventResponse> events = eventSlice.stream().map(EventResponse::from).toList();
        if (eventSlice.hasContent()) {
            Long id = eventSlice.getContent().getLast().getId();
            return EventSliceResponse.of(events, eventSlice.getSize(), id);
        }
        return EventSliceResponse.of(events, eventSlice.getSize(), lastId);
    }

    @Transactional(readOnly = true)
    public EventResponse getById(Long id) {
        return EventResponse.from(getByIdOrThrow(id));
    }

    public Event getByIdOrThrow(Long id) throws EntityNotFoundException {
        return eventRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Event"));
    }

    @Transactional
    public EventResponse create(Long hostId, EventCreateRequest eventCreateRequest) {
        return EventResponse.from(
            eventRepository.save(eventCreateRequest.toEntity(
            hostService.getByIdOrThrow(hostId),
            eventCreateRequest.getImageIds().stream()
                .map(eventImageService::getById)
                .toList())));
    }

    private void validateHostId(Long hostId, Event event) {
        if (!hostId.equals(event.getHost().getId())) {
            throw new UnauthorizedException("Host");
        }
    }

    @Transactional
    public EventResponse update(Long hostId, Long eventId, EventEditRequest eventEditRequest) {
        Event event = getByIdOrThrow(eventId);
        validateHostId(hostId, event);
        event.updateFrom(eventEditRequest, eventEditRequest.getImageIds().stream()
            .map(eventImageService::getById)
            .toList());
        return EventResponse.from(event);
    }

    @Transactional
    public void delete(Long hostId, Long eventId) {
        Event event = getByIdOrThrow(eventId);
        validateHostId(hostId, event);
        eventRepository.deleteById(eventId);
    }

    @Transactional
    public void decreaseLeftSeat(Long eventId, Integer count) {
        getByIdOrThrow(eventId).decreaseLeftSeat(count);
    }
}