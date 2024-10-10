package org.ktc2.cokaen.wouldyouin.advertisement.api;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import org.ktc2.cokaen.wouldyouin.advertisement.persist.Advertisement;

@Getter
@Builder(toBuilder = true)
public class AdvertisementRequest {

    private String title;
    private Long imageId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    public Advertisement toEntity() {
        return Advertisement.builder()
            .title(this.title)
            .imageId(this.imageId)
            .startTime(this.startTime)
            .endTime(this.endTime)
            .build();
    }
}
