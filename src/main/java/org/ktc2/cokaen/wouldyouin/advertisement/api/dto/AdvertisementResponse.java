package org.ktc2.cokaen.wouldyouin.advertisement.api.dto;

import java.time.LocalDateTime;
import lombok.Builder;
import org.ktc2.cokaen.wouldyouin.advertisement.persist.Advertisement;

@Builder
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
            .imageUrl(advertisement.getAdvertisementImage().getUrl())
            .startTime(advertisement.getStartTime())
            .endTime(advertisement.getEndTime())
            .build();
    }
}