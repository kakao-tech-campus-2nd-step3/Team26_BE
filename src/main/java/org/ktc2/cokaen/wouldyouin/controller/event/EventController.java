package org.ktc2.cokaen.wouldyouin.controller.event;

import java.util.List;
import java.util.UUID;
import org.ktc2.cokaen.wouldyouin.controller.event.response.ApiResponseBody;
import org.ktc2.cokaen.wouldyouin.service.EventService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/events")
public class EventController {

    private final EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping
    public ApiResponseBody<List<EventResponse>> getAllEvents() {
        return new ApiResponseBody<>(true, eventService.getAll());
    }

    @GetMapping
    public ApiResponseBody<List<EventResponse>> getEventsByHostId(@RequestParam UUID hostId) {
        return new ApiResponseBody<>(true, eventService.getAllByHostId(hostId));
    }

    @GetMapping("/{eventId}")
    public ApiResponseBody<EventResponse> getEventByEventId(@PathVariable("eventId") UUID eventId) {
        return new ApiResponseBody<>(true, eventService.getById(eventId));
    }

    @PostMapping
    public ApiResponseBody<EventResponse> createEvent(@RequestBody EventRequest eventRequest) {
        return new ApiResponseBody<>(true, eventService.create(eventRequest));
    }

    @PutMapping("/{eventId}")
    public ApiResponseBody<EventResponse> updateEvent(@PathVariable UUID eventId,
        @RequestBody EventRequest eventRequest) {
        return new ApiResponseBody<>(true, eventService.update(eventId, eventRequest));
    }

    @DeleteMapping("/{eventId}")
    public ApiResponseBody<Void> deleteEvent(@PathVariable("eventId") UUID eventId) {
        return new ApiResponseBody<>(true, null);
    }
}