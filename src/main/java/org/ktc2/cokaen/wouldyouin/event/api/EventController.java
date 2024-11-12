package org.ktc2.cokaen.wouldyouin.event.api;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.ktc2.cokaen.wouldyouin._common.api.ApiResponse;
import org.ktc2.cokaen.wouldyouin._common.api.ApiResponseBody;
import org.ktc2.cokaen.wouldyouin._common.api.ParamDefaults;
import org.ktc2.cokaen.wouldyouin._common.vo.Area;
import org.ktc2.cokaen.wouldyouin._common.vo.Category;
import org.ktc2.cokaen.wouldyouin.auth.Authorize;
import org.ktc2.cokaen.wouldyouin.auth.MemberIdentifier;
import org.ktc2.cokaen.wouldyouin.event.api.dto.EventCreateRequest;
import org.ktc2.cokaen.wouldyouin.event.api.dto.EventEditRequest;
import org.ktc2.cokaen.wouldyouin.event.api.dto.EventResponse;
import org.ktc2.cokaen.wouldyouin.event.api.dto.EventSliceResponse;
import org.ktc2.cokaen.wouldyouin.event.api.dto.LocationFilter;
import org.ktc2.cokaen.wouldyouin.event.api.dto.LocationRequest;
import org.ktc2.cokaen.wouldyouin.event.application.EventService;
import org.ktc2.cokaen.wouldyouin.member.persist.MemberType;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/events")
public class EventController {

    private final EventService eventService;

    @GetMapping
    public ResponseEntity<ApiResponseBody<EventSliceResponse>> getEventsByFilterOrderByDistanceAsc(
        @Valid @ModelAttribute LocationFilter locationFilter,
        @Valid @ModelAttribute LocationRequest currentLocation,
        @RequestParam(defaultValue = ParamDefaults.TITLE) String title,
        @RequestParam(defaultValue = ParamDefaults.CATEGORY) Category category,
        @RequestParam(defaultValue = ParamDefaults.AREA) Area area,
        @RequestParam(defaultValue = ParamDefaults.PAGE) Integer page,
        @RequestParam(defaultValue = ParamDefaults.PAGE_SIZE) Integer size,
        @RequestParam(defaultValue = ParamDefaults.LAST_ID) Long lastId
    ) {
        return ApiResponse.ok(eventService.getAllByFilterOrderByDistanceAsc(
            locationFilter, currentLocation, title, category, area, PageRequest.of(page, size),
            lastId));
    }

    @GetMapping("/hosts/{hostId}")
    public ResponseEntity<ApiResponseBody<EventSliceResponse>> getEventsByHostId(
        @PathVariable Long hostId,
        @RequestParam(defaultValue = ParamDefaults.PAGE) Integer page,
        @RequestParam(defaultValue = ParamDefaults.PAGE_SIZE) Integer size,
        @RequestParam(defaultValue = ParamDefaults.LAST_ID) Long lastId
    ) {
        return ApiResponse.ok(eventService.getAllByHostIdOrderByCreatedDateDesc(
            hostId, PageRequest.of(page, size), lastId));
    }

    @GetMapping("/{eventId}")
    public ResponseEntity<ApiResponseBody<EventResponse>> getEventByEventId(
        @PathVariable("eventId") Long eventId) {
        return ApiResponse.ok(eventService.getById(eventId));
    }

    @PostMapping
    public ResponseEntity<ApiResponseBody<EventResponse>> createEvent(
        @Valid @RequestBody EventCreateRequest eventCreateRequest,
        @Authorize(MemberType.host) MemberIdentifier host) {
        return ApiResponse.created(eventService.create(host.id(), eventCreateRequest));
    }

    @PutMapping("/{eventId}")
    public ResponseEntity<ApiResponseBody<EventResponse>> updateEvent(@PathVariable Long eventId,
        @Valid @RequestBody EventEditRequest eventEditRequest,
        @Authorize(MemberType.host) MemberIdentifier host) {
        return ApiResponse.ok(eventService.update(host.id(), eventId, eventEditRequest));
    }

    @DeleteMapping("/{eventId}")
    public ResponseEntity<ApiResponseBody<Void>> deleteEvent(
        @PathVariable("eventId") Long eventId,
        @Authorize(MemberType.host) MemberIdentifier host) {
        eventService.delete(host.id(), eventId);
        return ApiResponse.noContent();
    }
}