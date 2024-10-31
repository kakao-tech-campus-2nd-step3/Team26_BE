package org.ktc2.cokaen.wouldyouin.advertisement.persist;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.ktc2.cokaen.wouldyouin.Image.persist.AdvertisementImage;
import org.ktc2.cokaen.wouldyouin.advertisement.api.dto.AdvertisementRequest;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Advertisement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String title;

    @NotNull
    @OneToOne
    @JoinColumn(name = "image_id")
    private AdvertisementImage advertisementImage;

    @NotNull
    private LocalDateTime startTime;

    @NotNull
    private LocalDateTime endTime;

    @Builder
    public Advertisement(String title, AdvertisementImage advertisementImage, LocalDateTime startTime,
        LocalDateTime endTime) {
        this.title = title;
        this.advertisementImage = advertisementImage;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public void updateFrom(AdvertisementRequest advertisementRequest, AdvertisementImage adImage) {
        this.title = advertisementRequest.getTitle();
        this.advertisementImage = adImage;
        this.startTime = advertisementRequest.getStartTime();
        this.endTime = advertisementRequest.getEndTime();
    }
}