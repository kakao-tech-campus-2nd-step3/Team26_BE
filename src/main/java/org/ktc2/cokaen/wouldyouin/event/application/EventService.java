package org.ktc2.cokaen.wouldyouin.event.application;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.ktc2.cokaen.wouldyouin._common.api.EntityGettable;
import org.ktc2.cokaen.wouldyouin.event.api.EventRequest;
import org.ktc2.cokaen.wouldyouin.event.api.EventResponse;
import org.ktc2.cokaen.wouldyouin.event.persist.Event;
import org.ktc2.cokaen.wouldyouin.event.persist.EventRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor()
public class EventService implements EntityGettable<Event> {

    private final EventRepository eventRepository;

    @Transactional(readOnly = true)
    public List<EventResponse> getAll() {
        return eventRepository.findAll().stream().map(EventResponse::from).toList();
    }

    @Transactional(readOnly = true)
    public List<EventResponse> getAllByHostId(Long hostId) {
        return eventRepository.findByHostId(hostId).stream().map(EventResponse::from)
            .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public Event getByIdOrThrow(Long id) throws RuntimeException {
        return eventRepository.findById(id).orElseThrow(RuntimeException::new);
    }

    @Transactional(readOnly = true)
    public EventResponse getById(Long id) {
        return EventResponse.from(getByIdOrThrow(id));
    }

    @Transactional
    public EventResponse create(EventRequest eventRequest) {
        return EventResponse.from(eventRepository.save(eventRequest.toEntity()));
    }

    @Transactional
    public EventResponse update(Long id, EventRequest eventRequest) {
        Event target = eventRepository.findById(id).orElseThrow(RuntimeException::new);
        target.updateFrom(eventRequest);
        return EventResponse.from(target);
    }

    @Transactional
    public void delete(Long id) {
        eventRepository.findById(id).orElseThrow(RuntimeException::new);
        eventRepository.deleteById(id);
    }
}