package org.ktc2.cokaen.wouldyouin.event.api;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.ktc2.cokaen.wouldyouin._common.api.ApiResponse;
import org.ktc2.cokaen.wouldyouin._common.api.ApiResponseBody;
import org.ktc2.cokaen.wouldyouin.event.api.dto.EventCreateRequest;
import org.ktc2.cokaen.wouldyouin.event.api.dto.EventEditRequest;
import org.ktc2.cokaen.wouldyouin.event.api.dto.EventResponse;
import org.ktc2.cokaen.wouldyouin.event.application.EventService;
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
        return ApiResponse.ok(eventService.getAll());
    }

    @GetMapping("/hosts/{hostId}")
    public ResponseEntity<ApiResponseBody<List<EventResponse>>> getEventsByHostId(
        @PathVariable Long hostId) {
        return ApiResponse.ok(eventService.getAllByHostId(hostId));
    }

    @GetMapping("/{eventId}")
    public ResponseEntity<ApiResponseBody<EventResponse>> getEventByEventId(
        @PathVariable("eventId") Long eventId) {
        return ApiResponse.ok(eventService.getById(eventId));
    }

    @PostMapping
    public ResponseEntity<ApiResponseBody<EventResponse>> createEvent(
        @RequestBody EventCreateRequest eventCreateRequest) {
        return ApiResponse.created(eventService.create(eventCreateRequest));
    }

    @PutMapping("/{eventId}")
    public ResponseEntity<ApiResponseBody<EventResponse>> updateEvent(@PathVariable Long eventId,
        @RequestBody EventEditRequest eventEditRequest) {
        return ApiResponse.ok(eventService.update(eventId, eventEditRequest));
    }

    @DeleteMapping("/{eventId}")
    public ResponseEntity<ApiResponseBody<Void>> deleteEvent(
        @PathVariable("eventId") Long eventId) {
        eventService.delete(eventId);
        return ApiResponse.noContent();
    }
}