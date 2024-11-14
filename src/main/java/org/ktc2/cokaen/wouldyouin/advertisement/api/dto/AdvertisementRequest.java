package org.ktc2.cokaen.wouldyouin.advertisement.api.dto;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import org.ktc2.cokaen.wouldyouin.image.persist.AdvertisementImage;
import org.ktc2.cokaen.wouldyouin.advertisement.persist.Advertisement;

@Getter
@Builder(toBuilder = true)
public class AdvertisementRequest {

    @NotBlank(message = "광고명은 필수입니다.")
    private String title;

    @NotNull(message = "광고게시 시작 시간은 필수입니다.")
    @FutureOrPresent(message = "광고게시 시작 시간은 현재 시간 이후여야 합니다.")
    private LocalDateTime startTime;

    @NotNull(message = "광고게시 종료시간은 필수입니다.")
    @FutureOrPresent(message = "광고게시 종료 시간은 현재 시간 이후여야 합니다.")
    private LocalDateTime endTime;

    @AssertTrue(message = "종료 시간은 시작 시간 이후여야 합니다.")
    public boolean isEndTimeAfterStartTime() {
        return endTime.isAfter(startTime);
    }

    public Advertisement toEntity(AdvertisementImage adImage) {
        return Advertisement.builder()
            .title(this.title)
            .advertisementImage(adImage)
            .startTime(this.startTime)
            .endTime(this.endTime)
            .build();
    }
}
