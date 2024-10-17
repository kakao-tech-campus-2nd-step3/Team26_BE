package org.ktc2.cokaen.wouldyouin.event.persist;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.ktc2.cokaen.wouldyouin.Image.persist.EventImage;
import org.ktc2.cokaen.wouldyouin._common.persist.Area;
import org.ktc2.cokaen.wouldyouin._common.persist.Category;
import org.ktc2.cokaen.wouldyouin._common.persist.Location;
import org.ktc2.cokaen.wouldyouin.event.api.dto.EventEditRequest;
import org.ktc2.cokaen.wouldyouin.member.persist.Host;

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
    @Column(name = "title")
    private String title;

    @NotNull
    @Column(name = "content")
    private String content;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "host_id")
    private Host host;

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

    @OneToMany(mappedBy = "event")
    private List<EventImage> images;

    @Builder
    protected Event(String title, String content, Host host, Area area, Location location,
        LocalDateTime startTime, LocalDateTime endTime, Integer price, Integer totalSeat,
        Integer leftSeat, Category category, List<EventImage> images) {
        this.title = title;
        this.content = content;
        this.host = host;
        this.area = area;
        this.location = location;
        this.startTime = startTime;
        this.endTime = endTime;
        this.price = price;
        this.totalSeat = totalSeat;
        this.leftSeat = leftSeat;
        this.category = category;
        this.images = images;
    }

    public void updateFrom(EventEditRequest eventEditRequest) {
        Optional.ofNullable(eventEditRequest.getTitle()).ifPresent(this::setTitle);
        Optional.ofNullable(eventEditRequest.getContent()).ifPresent(this::setContent);
        Optional.ofNullable(eventEditRequest.getArea()).ifPresent(this::setArea);
        Optional.ofNullable(eventEditRequest.getLocation()).ifPresent(this::setLocation);
        Optional.ofNullable(eventEditRequest.getStartTime()).ifPresent(this::setStartTime);
        Optional.ofNullable(eventEditRequest.getEndTime()).ifPresent(this::setEndTime);
        Optional.ofNullable(eventEditRequest.getPrice()).ifPresent(this::setPrice);
        Optional.ofNullable(eventEditRequest.getTotalSeat()).ifPresent(this::setTotalSeat);
        Optional.ofNullable(eventEditRequest.getLeftSeat()).ifPresent(this::setLeftSeat);
        Optional.ofNullable(eventEditRequest.getCategory()).ifPresent(this::setCategory);
//        Optional.ofNullable(eventEditRequest.getImageUrls()).ifPresent(this::setImages);
    }
}