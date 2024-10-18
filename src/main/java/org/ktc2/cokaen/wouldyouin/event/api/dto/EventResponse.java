package org.ktc2.cokaen.wouldyouin.event.api.dto;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import org.ktc2.cokaen.wouldyouin._common.persist.Area;
import org.ktc2.cokaen.wouldyouin._common.persist.Category;
import org.ktc2.cokaen.wouldyouin._common.persist.Location;
import org.ktc2.cokaen.wouldyouin.event.persist.Event;
import org.ktc2.cokaen.wouldyouin.member.application.dto.relationResponse.EventHostResponse;
import org.ktc2.cokaen.wouldyouin.member.persist.Host;

@Builder
@Getter
public class EventResponse {

    private Long id;
    private String title;
    private String content;
    private EventHostResponse host;
    private Area area;
    private Location location;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Integer price;
    private Integer totalSeat;
    private Integer leftSeat;
    private Category category;
    private Boolean expired;

    public static EventResponse from(Event event) {
        Host host = event.getHost();
        return EventResponse.builder()
            .id(event.getId())
            .title(event.getTitle())
            .content(event.getContent())
            .host(EventHostResponse.builder()
                .nickname(host.getNickname())
                .email(host.getEmail())
                .phone(host.getPhone())
                .profileImageUrl(host.getProfileImageUrl())
                .intro(host.getIntro())
                .likes(host.getLikes())
                .hashtags(host.getHashTagList())
                .build())
            .area(event.getArea())
            .location(event.getLocation())
            .startTime(event.getStartTime())
            .endTime(event.getEndTime())
            .price(event.getPrice())
            .totalSeat(event.getTotalSeat())
            .leftSeat(event.getLeftSeat())
            .category(event.getCategory())
            .expired(event.getStartTime().isAfter(LocalDateTime.now()))
            .build();
    }
}