package org.ktc2.cokaen.wouldyouin.event.application;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.ktc2.cokaen.wouldyouin.Image.application.EventImageService;
import org.ktc2.cokaen.wouldyouin._common.exception.EntityNotFoundException;
import org.ktc2.cokaen.wouldyouin._common.persist.Area;
import org.ktc2.cokaen.wouldyouin._common.persist.Category;
import org.ktc2.cokaen.wouldyouin._common.persist.Location;
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
                location.getEndLatitude(), location.getEndLongitude(),
                currentLocation.getLatitude(), currentLocation.getLongitude(), category, area, pageable), lastId
        );
    }

    @Transactional(readOnly = true)
    public EventSliceResponse getAllByHostIdOrderByCreatedDateDesc(Long hostId, Pageable pageable, Long lastId) {
        return getEventSliceResponse(
            eventRepository.findAllByHostIdOrderByEventIdDesc(hostId, lastId, pageable), lastId);
    }

    private EventSliceResponse getEventSliceResponse(Slice<Event> eventSlice, Long lastId) {
        List<EventResponse> events = eventSlice.stream().map(EventResponse::from).toList();
        if (!eventSlice.hasContent()) {
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
    public EventResponse create(EventCreateRequest eventCreateRequest) {
        return EventResponse.from(eventRepository.save(eventCreateRequest.toEntity(
            hostService.getByIdOrThrow(eventCreateRequest.getHostId()),
            eventCreateRequest.getImageIds().stream()
                .map(eventImageService::getById)
                .toList())));
    }

    @Transactional
    public EventResponse update(Long id, EventEditRequest eventEditRequest) {
        Event target = getByIdOrThrow(id);
        target.updateFrom(eventEditRequest, eventEditRequest.getImageIds().stream()
            .map(eventImageService::getById)
            .toList());
        return EventResponse.from(target);
    }

    @Transactional
    public void decreaseLeftSeat(Long id, Integer count) {
        getByIdOrThrow(id).decreaseLeftSeat(count);
    }

    @Transactional
    public void delete(Long id) {
        getByIdOrThrow(id);
        eventRepository.deleteById(id);
    }
}