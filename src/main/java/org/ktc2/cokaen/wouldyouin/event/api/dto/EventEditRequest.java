package org.ktc2.cokaen.wouldyouin.event.api.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.ktc2.cokaen.wouldyouin._common.vo.Area;
import org.ktc2.cokaen.wouldyouin._common.vo.Category;
import org.ktc2.cokaen.wouldyouin._common.vo.Location;

@Getter
@Builder(toBuilder = true)
@EqualsAndHashCode
@ToString
public class EventEditRequest {

    @NotBlank(message = "제목은 필수입니다.")
    private String title;

    @NotBlank(message = "내용은 필수입니다.")
    @Size(min = 20, max = 1000, message = "내용은 20자 이상 1000자 이하입니다.")
    private String content;

    @NotNull(message = "지역는 필수입니다.")
    private Area area;

    @Valid
    @NotNull(message = "장소는 필수입니다.")
    private Location location;

    @NotNull(message = "시작 시간은 필수입니다.")
    @FutureOrPresent(message = "시작 시간은 현재 시간 이후여야 합니다.")
    private LocalDateTime startTime;

    @NotNull(message = "종료 시간은 필수입니다.")
    @FutureOrPresent(message = "종료 시간은 현재 시간 이후여야 합니다.")
    private LocalDateTime endTime;

    @NotNull(message = "가격은 필수입니다.")
    @Min(value = 0, message = "가격은 0원 이상입니다.")
    @Max(value = 1000000, message = "가격은 1,000,000원 이하입니다.")
    private Integer price;

    @NotNull(message = "좌석은 필수입니다.")
    @Min(value = 0, message = "총 좌석은 0석 이상입니다.")
    @Max(value = 1000, message = "총 좌석은 1,000석 이하입니다.")
    private Integer totalSeat;

    @NotNull(message = "카테고리는 필수입니다.")
    private Category category;

    private List<Long> imageIds;

    @AssertTrue(message = "종료 시간은 시작 시간 이후여야 합니다.")
    public boolean isEndTimeAfterStartTime() {
        if (startTime == null || endTime == null) {
            return true;
        }
        return endTime.isAfter(startTime);
    }

    @AssertTrue(message = "이미지는 최대 5개까지 등록할 수 있습니다.")
    public boolean isImageSizeValid() {
        return imageIds.size() <= 5;
    }
}