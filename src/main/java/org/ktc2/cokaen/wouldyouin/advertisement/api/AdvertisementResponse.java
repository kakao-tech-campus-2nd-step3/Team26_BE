package org.ktc2.cokaen.wouldyouin.advertisement.api;

import java.time.LocalDateTime;
import lombok.Builder;
import org.ktc2.cokaen.wouldyouin.advertisement.persist.Advertisement;

@Builder
public class AdvertisementResponse {

    private Long id;
    private String title;
    private Long imageId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    public static AdvertisementResponse from(Advertisement advertisement) {
        return AdvertisementResponse.builder()
            .id(advertisement.getId())
            .title(advertisement.getTitle())
            .imageId(advertisement.getImageId())
            .startTime(advertisement.getStartTime())
            .endTime(advertisement.getEndTime())
            .build();
    }
}
