package org.ktc2.cokaen.wouldyouin.advertisement.api.dto;

import java.time.LocalDateTime;
import java.util.Optional;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.ktc2.cokaen.wouldyouin.advertisement.persist.Advertisement;
import org.ktc2.cokaen.wouldyouin.image.persist.AdvertisementImage;
import org.ktc2.cokaen.wouldyouin.image.persist.Image;

@Builder
@EqualsAndHashCode
@ToString
public class AdvertisementResponse {

    private Long id;
    private String title;
    private String imageUrl;
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    public static AdvertisementResponse from(Advertisement advertisement) {
        return AdvertisementResponse.builder()
            .id(advertisement.getId())
            .title(advertisement.getTitle())
            .imageUrl(Optional.of(advertisement)
                .map((ad) -> advertisement.getAdvertisementImage())
                .map(Image::getName)
                .orElse(""))
            .startTime(advertisement.getStartTime())
            .endTime(advertisement.getEndTime())
            .build();
    }
}