package org.ktc2.cokaen.wouldyouin.event.application;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.ktc2.cokaen.wouldyouin.Image.application.EventImageService;
import org.ktc2.cokaen.wouldyouin._common.exception.EntityNotFoundException;
import org.ktc2.cokaen.wouldyouin.event.api.dto.EventCreateRequest;
import org.ktc2.cokaen.wouldyouin.event.api.dto.EventEditRequest;
import org.ktc2.cokaen.wouldyouin.event.api.dto.EventResponse;
import org.ktc2.cokaen.wouldyouin.event.persist.Event;
import org.ktc2.cokaen.wouldyouin.event.persist.EventRepository;
import org.ktc2.cokaen.wouldyouin.member.application.HostService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class EventService {

    private final EventRepository eventRepository;
    private final HostService hostService;
    private final EventImageService eventImageService;

    @Transactional(readOnly = true)
    public List<EventResponse> getAll() {
        return eventRepository.findAll().stream().map(EventResponse::from).toList();
    }

    @Transactional(readOnly = true)
    public List<EventResponse> getAllByHostId(Long hostId) {
        return eventRepository.findByHostId(hostId).stream().map(EventResponse::from).toList();
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
                .map(eventImageService::getByIdOrThrow)
                .toList())));
    }

    @Transactional
    public EventResponse update(Long id, EventEditRequest eventEditRequest) {
        Event target = getByIdOrThrow(id);
        target.updateFrom(eventEditRequest, eventEditRequest.getImageIds().stream()
            .map(eventImageService::getByIdOrThrow)
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