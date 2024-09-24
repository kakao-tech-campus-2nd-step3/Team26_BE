package org.ktc2.cokaen.wouldyouin.service;

import static org.ktc2.cokaen.wouldyouin.controller.event.EventResponse.toEventResponse;
import static org.ktc2.cokaen.wouldyouin.domain.Event.from;

import java.util.List;
import java.util.UUID;
import org.ktc2.cokaen.wouldyouin.controller.event.EventRequest;
import org.ktc2.cokaen.wouldyouin.controller.event.EventResponse;
import org.ktc2.cokaen.wouldyouin.domain.Event;
import org.ktc2.cokaen.wouldyouin.repository.EventRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EventService {

    private final EventRepository eventRepository;

    public EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    @Transactional(readOnly = true)
    public List<EventResponse> getAll() {
        return eventRepository.findAll().stream().map(EventResponse::toEventResponse).toList();
    }

    @Transactional(readOnly = true)
    public List<EventResponse> getAllByHostId(UUID hostId) {
        return eventRepository.findByHostId(hostId).stream().map(EventResponse::toEventResponse)
            .toList();
    }

    @Transactional(readOnly = true)
    public EventResponse getById(UUID id) {
        Event target = eventRepository.findById(id).orElseThrow(RuntimeException::new);
        return toEventResponse(target);
    }

    @Transactional
    public EventResponse create(EventRequest eventRequest) {
        return toEventResponse(eventRepository.save(from(eventRequest)));
    }

    @Transactional
    public EventResponse update(UUID id, EventRequest eventRequest) {
        Event target = eventRepository.findById(id).orElseThrow(RuntimeException::new);
        target.setFrom(eventRequest);
        return toEventResponse(target);
    }

    @Transactional
    public void delete(UUID id) {
        eventRepository.findById(id).orElseThrow(RuntimeException::new);
        eventRepository.deleteById(id);
    }
}