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
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.ktc2.cokaen.wouldyouin.Image.persist.EventImage;
import org.ktc2.cokaen.wouldyouin._common.exception.NoLeftSeatException;
import org.ktc2.cokaen.wouldyouin._common.persist.Area;
import org.ktc2.cokaen.wouldyouin._common.persist.Category;
import org.ktc2.cokaen.wouldyouin._common.persist.Location;
import org.ktc2.cokaen.wouldyouin.event.api.dto.EventEditRequest;
import org.ktc2.cokaen.wouldyouin.member.persist.Host;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
@Entity
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "event_id")
    private Long id;

    @NotNull
    @Column(name = "title")
    private String title;

    @NotBlank
    @Column(name = "content")
    private String content;

    @NotNull
    @ManyToOne
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

    @Column(name = "start_time")
    private LocalDateTime startTime;

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

    // Todo: 이미지를 사용하는 모든 엔티티 thumnail 설정
    @Column(name = "thumbnail_url")
    private String thumbnailUrl;

    @OneToMany(mappedBy = "event", fetch = FetchType.LAZY)
    private List<EventImage> images = new ArrayList<>();

    @CreatedDate
    @Column(name = "created_date")
    private LocalDateTime createdDate;

    @Builder
    protected Event(String title, String content, Host host, Area area, Location location,
        LocalDateTime startTime, LocalDateTime endTime, Integer price, Integer totalSeat, Category category, List<EventImage> images) {
        this.title = title;
        this.content = content;
        this.host = host;
        this.area = area;
        this.location = location;
        this.startTime = startTime;
        this.endTime = endTime;
        this.price = price;
        this.totalSeat = totalSeat;
        this.leftSeat = totalSeat;
        this.category = category;
        this.images = images;
    }

    public void updateFrom(EventEditRequest eventEditRequest, List<EventImage> images) {
        this.title = eventEditRequest.getTitle();
        this.content = eventEditRequest.getContent();
        this.area = eventEditRequest.getArea();
        this.location = eventEditRequest.getLocation();
        this.startTime = eventEditRequest.getStartTime();
        this.endTime = eventEditRequest.getEndTime();
        this.price = eventEditRequest.getPrice();
        this.totalSeat = eventEditRequest.getTotalSeat();
        this.category = eventEditRequest.getCategory();
        this.images = images;
    }

    public void decreaseLeftSeat(Integer count) {
        if (this.leftSeat < count) {
            throw new NoLeftSeatException();
        }
        this.leftSeat -= count;
    }
}