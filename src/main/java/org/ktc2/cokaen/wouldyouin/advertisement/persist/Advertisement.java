package org.ktc2.cokaen.wouldyouin.advertisement.persist;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.ktc2.cokaen.wouldyouin.advertisement.api.AdvertisementRequest;

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
    private Long imageId;

    @NotNull
    private LocalDateTime startTime;

    @NotNull
    private LocalDateTime endTime;

    @Builder
    public Advertisement(String title, Long imageId, LocalDateTime startTime,
        LocalDateTime endTime) {
        this.title = title;
        this.imageId = imageId;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public void updateFrom(AdvertisementRequest advertisementRequest) {
        this.title = advertisementRequest.getTitle();
        this.imageId = advertisementRequest.getImageId();
        this.startTime = advertisementRequest.getStartTime();
        this.endTime = advertisementRequest.getEndTime();
    }
}
