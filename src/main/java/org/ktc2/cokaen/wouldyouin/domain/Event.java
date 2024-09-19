package org.ktc2.cokaen.wouldyouin.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;
import org.ktc2.cokaen.wouldyouin.controller.event.EventRequest;

@Getter
@Setter
@Entity
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(nullable = false)
    private UUID hostId;
    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    private String content;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Area area;
    @Column(nullable = false)
    private String location;
    private LocalDateTime start_time;
    private LocalDateTime end_time;
    private Integer price;
    private Integer totalSeat;
    private Integer leftSeat;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    Category category;
    private Boolean expired;

    public Event() {
    }

    public Event(UUID hostId, String title, String content, Area area, String location,
        LocalDateTime start_time, LocalDateTime end_time, Integer price, Integer totalSeat,
        Integer leftSeat, Category category) {
        this.hostId = hostId;
        this.title = title;
        this.content = content;
        this.area = area;
        this.location = location;
        this.start_time = start_time;
        this.end_time = end_time;
        this.price = price;
        this.totalSeat = totalSeat;
        this.leftSeat = leftSeat;
        this.category = category;
    }

    @PrePersist
    public void prePersist() {
        if (this.expired == null) {
            this.expired = false;
        }
    }

    public void setDetails(EventRequest eventRequest) {
        this.hostId = eventRequest.getHostId();
        this.title = eventRequest.getTitle();
        this.content = eventRequest.getContent();
        this.area = eventRequest.getArea();
        this.location = eventRequest.getLocation();
        this.start_time = eventRequest.getStart_time();
        this.end_time = eventRequest.getEnd_time();
        this.price = eventRequest.getPrice();
        this.totalSeat = eventRequest.getTotalSeat();
        this.leftSeat = eventRequest.getLeftSeat();
        this.category = eventRequest.getCategory();
    }

    public static Event from(EventRequest eventRequest) {
        return new Event(eventRequest.getHostId(), eventRequest.getTitle(),
            eventRequest.getContent(), eventRequest.getArea(), eventRequest.getLocation(),
            eventRequest.getStart_time(), eventRequest.getEnd_time(), eventRequest.getPrice(),
            eventRequest.getTotalSeat(), eventRequest.getLeftSeat(), eventRequest.getCategory());
    }
}