package org.ktc2.cokaen.wouldyouin.event.api;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.ktc2.cokaen.wouldyouin._common.api.ApiResponseBody;
import org.ktc2.cokaen.wouldyouin.event.application.EventService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/events")
public class EventController {

    private final EventService eventService;

    @GetMapping
    public ResponseEntity<ApiResponseBody<List<EventResponse>>> getEvents() {
        return ResponseEntity.status(HttpStatus.OK)
            .body(new ApiResponseBody<>(true, eventService.getAll()));
    }

    @GetMapping("/hosts/{hostId}")
    public ResponseEntity<ApiResponseBody<List<EventResponse>>> getEventsByHostId(
        @PathVariable Long hostId) {
        return ResponseEntity.status(HttpStatus.OK)
            .body(new ApiResponseBody<>(true, eventService.getAllByHostId(hostId)));
    }

    @GetMapping("/{eventId}")
    public ResponseEntity<ApiResponseBody<EventResponse>> getEventByEventId(
        @PathVariable("eventId") Long eventId) {
        return ResponseEntity.status(HttpStatus.OK)
            .body(new ApiResponseBody<>(true, eventService.getById(eventId)));
    }

    @PostMapping
    public ResponseEntity<ApiResponseBody<EventResponse>> createEvent(
        @RequestBody EventRequest eventRequest) {
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(new ApiResponseBody<>(true, eventService.create(eventRequest)));
    }

    @PutMapping("/{eventId}")
    public ResponseEntity<ApiResponseBody<EventResponse>> updateEvent(@PathVariable Long eventId,
        @RequestBody EventRequest eventRequest) {
        return ResponseEntity.status(HttpStatus.OK)
            .body(new ApiResponseBody<>(true, eventService.update(eventId, eventRequest)));
    }

    @DeleteMapping("/{eventId}")
    public ResponseEntity<ApiResponseBody<Void>> deleteEvent(
        @PathVariable("eventId") Long eventId) {
        eventService.delete(eventId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT)
            .body(new ApiResponseBody<>(true, null));
    }
}