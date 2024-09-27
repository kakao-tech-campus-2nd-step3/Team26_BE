package org.ktc2.cokaen.wouldyouin.domain;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.ktc2.cokaen.wouldyouin.controller.event.EventRequest;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private Long hostId;

    @NotNull
    private String title;

    @NotNull
    private String content;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Area area;

    @Embedded
    @NotNull
    private Location location;

    @NotNull
    private LocalDateTime startTime;

    @NotNull
    private LocalDateTime endTime;

    @NotNull
    @Min(0)
    private Integer price;

    @NotNull
    @Min(0)
    private Integer totalSeat;

    @NotNull
    @Min(0)
    private Integer leftSeat;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Category category;

    @Builder
    protected Event(Long hostId, String title, String content, Area area, Location location,
        LocalDateTime startTime, LocalDateTime endTime, Integer price, Integer totalSeat,
        Integer leftSeat, Category category) {
        this.hostId = hostId;
        this.title = title;
        this.content = content;
        this.area = area;
        this.location = location;
        this.startTime = startTime;
        this.endTime = endTime;
        this.price = price;
        this.totalSeat = totalSeat;
        this.leftSeat = leftSeat;
        this.category = category;
    }

    public void setFrom(EventRequest eventRequest) {
        this.hostId = eventRequest.getHostId();
        this.title = eventRequest.getTitle();
        this.content = eventRequest.getContent();
        this.area = eventRequest.getArea();
        this.location = eventRequest.getLocation();
        this.startTime = eventRequest.getStartTime();
        this.endTime = eventRequest.getEndTime();
        this.price = eventRequest.getPrice();
        this.totalSeat = eventRequest.getTotalSeat();
        this.leftSeat = eventRequest.getLeftSeat();
        this.category = eventRequest.getCategory();
    }
}