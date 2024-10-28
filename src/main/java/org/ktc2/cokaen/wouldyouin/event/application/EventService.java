package org.ktc2.cokaen.wouldyouin.event.application;

import java.util.List;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.ktc2.cokaen.wouldyouin.Image.persist.EventImage;
import org.ktc2.cokaen.wouldyouin._common.api.EntityGettable;
import org.ktc2.cokaen.wouldyouin.event.api.dto.EventCreateRequest;
import org.ktc2.cokaen.wouldyouin.event.api.dto.EventEditRequest;
import org.ktc2.cokaen.wouldyouin.event.api.dto.EventResponse;
import org.ktc2.cokaen.wouldyouin.event.persist.Event;
import org.ktc2.cokaen.wouldyouin.event.persist.EventRepository;
import org.ktc2.cokaen.wouldyouin.member.persist.Host;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor(access = AccessLevel.PUBLIC)
public class EventService implements EntityGettable<Long, Event> {

    private final EventRepository eventRepository;
    private final EntityGettable<Long, Host> hostService;
    private final EntityGettable<List<Long>, List<EventImage>> eventImageService;

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

    @Override
    public Event getByIdOrThrow(Long id) throws RuntimeException {
        return eventRepository.findById(id).orElseThrow(RuntimeException::new);
    }

    @Transactional
    public EventResponse create(EventCreateRequest eventCreateRequest) {
        Event event = eventRepository.save(eventCreateRequest.toEntity());
        event.setHost(hostService.getByIdOrThrow(eventCreateRequest.getHostId()));
        event.setImages(eventImageService.getByIdOrThrow(eventCreateRequest.getImageIds()));
        return EventResponse.from(event);
    }

    @Transactional
    public EventResponse update(Long id, EventEditRequest eventEditRequest) {
        Event target = eventRepository.findById(id).orElseThrow(RuntimeException::new);
        target.updateFrom(eventEditRequest);
        target.setImages(eventImageService.getByIdOrThrow(eventEditRequest.getImageIds()));
        return EventResponse.from(target);
    }

    @Transactional
    public void delete(Long id) {
        eventRepository.findById(id).orElseThrow(RuntimeException::new);
        eventRepository.deleteById(id);
    }
}