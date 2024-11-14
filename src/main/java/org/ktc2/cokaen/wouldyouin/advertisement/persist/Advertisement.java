package org.ktc2.cokaen.wouldyouin.advertisement.persist;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Optional;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.ktc2.cokaen.wouldyouin.image.persist.AdvertisementImage;
import org.ktc2.cokaen.wouldyouin.advertisement.api.dto.AdvertisementRequest;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Advertisement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    @Column(name = "advertisement_id")
    private Long id;

    @NotBlank
    @Column(name = "title")
    private String title;

    @NotNull
    @OneToOne
    @JoinColumn(name = "image_id")
    private AdvertisementImage advertisementImage;

    @NotNull
    @Column(name = "start_time")
    private LocalDateTime startTime;

    @NotNull
    @Column(name = "end_time")
    private LocalDateTime endTime;

    @Builder
    public Advertisement(String title, AdvertisementImage advertisementImage,
        LocalDateTime startTime,
        LocalDateTime endTime) {
        this.title = title;
        this.advertisementImage = advertisementImage;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public void updateFrom(AdvertisementRequest advertisementRequest, AdvertisementImage adImage) {
        Optional.ofNullable(advertisementRequest.getTitle()).ifPresent(this::setTitle);
        Optional.ofNullable(adImage).ifPresent(this::setAdvertisementImage);
        Optional.ofNullable(advertisementRequest.getStartTime()).ifPresent(this::setStartTime);
        Optional.ofNullable(advertisementRequest.getEndTime()).ifPresent(this::setEndTime);
    }
}