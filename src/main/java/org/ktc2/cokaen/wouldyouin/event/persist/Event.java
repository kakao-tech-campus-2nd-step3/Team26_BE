package org.ktc2.cokaen.wouldyouin.event.persist;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.ktc2.cokaen.wouldyouin._common.vo.Area;
import org.ktc2.cokaen.wouldyouin._common.vo.Category;
import org.ktc2.cokaen.wouldyouin._common.vo.Location;
import org.ktc2.cokaen.wouldyouin.event.api.dto.EventEditRequest;
import org.ktc2.cokaen.wouldyouin.image.persist.EventImage;
import org.ktc2.cokaen.wouldyouin.member.persist.Host;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Getter
@Setter
@EqualsAndHashCode
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
@Entity
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    @Column(name = "event_id")
    private Long id;

    @NotBlank
    @Column(name = "title")
    private String title;

    @NotBlank
    @Column(name = "content")
    private String content;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
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

    @NotNull
    @Min(1)
    @Column(name = "total_seat")
    private Integer totalSeat;

    @NotNull
    @Min(0)
    @Column(name = "left_seat")
    private Integer leftSeat;

    @NotNull
    @Column(name = "category")
    @Enumerated(EnumType.STRING)
    private Category category;

    @Column(name = "thumbnail_url")
    private String thumbnailUrl;

    @OneToMany(mappedBy = "event", fetch = FetchType.LAZY)
    private List<EventImage> images = new ArrayList<>();

    @NotNull
    @CreatedDate
    @Column(name = "created_date")
    private LocalDateTime createdDate;

    @Builder
    protected Event(String title, String content, Host host, Area area, Location location,
        LocalDateTime startTime, LocalDateTime endTime, Integer price, Integer totalSeat,
        Category category, List<EventImage> images, String thumbnailUrl) {
        Optional.ofNullable(title).ifPresent(this::setTitle);
        Optional.ofNullable(content).ifPresent(this::setContent);
        Optional.ofNullable(host).ifPresent(this::setHost);
        Optional.ofNullable(area).ifPresent(this::setArea);
        Optional.ofNullable(location).ifPresent(this::setLocation);
        Optional.ofNullable(startTime).ifPresent(this::setStartTime);
        Optional.ofNullable(endTime).ifPresent(this::setEndTime);
        Optional.ofNullable(price).ifPresent(this::setPrice);
        Optional.ofNullable(totalSeat).ifPresent(this::setTotalSeat);
        Optional.ofNullable(leftSeat).ifPresent(this::setLeftSeat);
        Optional.ofNullable(category).ifPresent(this::setCategory);
        Optional.ofNullable(images).ifPresent(this::setImages);
        Optional.of(thumbnailUrl).ifPresent(this::setThumbnailUrl);
    }

    public void updateFrom(EventEditRequest eventEditRequest, List<EventImage> images, String thumbnailUrl) {
        Optional.ofNullable(eventEditRequest.getTitle()).ifPresent(this::setTitle);
        Optional.ofNullable(eventEditRequest.getContent()).ifPresent(this::setContent);
        Optional.ofNullable(eventEditRequest.getArea()).ifPresent(this::setArea);
        Optional.ofNullable(eventEditRequest.getLocation()).ifPresent(this::setLocation);
        Optional.ofNullable(eventEditRequest.getStartTime()).ifPresent(this::setStartTime);
        Optional.ofNullable(eventEditRequest.getEndTime()).ifPresent(this::setEndTime);
        Optional.ofNullable(eventEditRequest.getPrice()).ifPresent(this::setPrice);
        Optional.ofNullable(eventEditRequest.getTotalSeat()).ifPresent(this::setTotalSeat);
        Optional.ofNullable(eventEditRequest.getCategory()).ifPresent(this::setCategory);
        Optional.ofNullable(images).ifPresent(this::setImages);
        Optional.ofNullable(thumbnailUrl).ifPresent(this::setThumbnailUrl);
    }

    public void decreaseLeftSeat(Integer count) {
        this.leftSeat -= count;
    }
}