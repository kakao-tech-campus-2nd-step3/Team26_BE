package org.ktc2.cokaen.wouldyouin.event.persist;

import jakarta.persistence.Column;
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
import java.util.Optional;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.ktc2.cokaen.wouldyouin._common.persist.Area;
import org.ktc2.cokaen.wouldyouin._common.persist.Category;
import org.ktc2.cokaen.wouldyouin._common.persist.Location;
import org.ktc2.cokaen.wouldyouin.event.api.EventRequest;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "event_id")
    private Long id;

    @NotNull
    @Column(name = "host_id")
    private Long hostId;

    @NotNull
    @Column(name = "title")
    private String title;

    @NotNull
    @Column(name = "content")
    private String content;

    @NotNull
    @Column(name = "area")
    @Enumerated(EnumType.STRING)
    private Area area;

    @Embedded
    @NotNull
    @Column(name = "location")
    private Location location;

    @NotNull
    @Column(name = "start_time")
    private LocalDateTime startTime;

    @NotNull
    @Column(name = "end_time")
    private LocalDateTime endTime;

    @NotNull
    @Min(0)
    @Column(name = "price")
    private Integer price;

    @Min(1)
    @Column(name = "total_seat")
    private Integer totalSeat;

    @Min(0)
    @Column(name = "left_seat")
    private Integer leftSeat;

    @NotNull
    @Column(name = "category")
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

    public void updateFrom(EventRequest eventRequest) {
        Optional.ofNullable(eventRequest.getHostId()).ifPresent(this::setHostId);
        Optional.ofNullable(eventRequest.getTitle()).ifPresent(this::setTitle);
        Optional.ofNullable(eventRequest.getContent()).ifPresent(this::setContent);
        Optional.ofNullable(eventRequest.getArea()).ifPresent(this::setArea);
        Optional.ofNullable(eventRequest.getLocation()).ifPresent(this::setLocation);
        Optional.ofNullable(eventRequest.getStartTime()).ifPresent(this::setStartTime);
        Optional.ofNullable(eventRequest.getEndTime()).ifPresent(this::setEndTime);
        Optional.ofNullable(eventRequest.getPrice()).ifPresent(this::setPrice);
        Optional.ofNullable(eventRequest.getTotalSeat()).ifPresent(this::setTotalSeat);
        Optional.ofNullable(eventRequest.getLeftSeat()).ifPresent(this::setLeftSeat);
        Optional.ofNullable(eventRequest.getCategory()).ifPresent(this::setCategory);
    }
}