package org.ktc2.cokaen.wouldyouin.event.api.dto;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LocationFilter {

    @Min(value = -90, message = "시작 위도는 -90 이상이어야 합니다.")
    @Max(value = 90, message = "시작 위도는 90 이하여야 합니다.")
    private Double startLatitude = -90.0;

    @Min(value = -180, message = "시작 경도는 -180 이상이어야 합니다.")
    @Max(value = 180, message = "시작 경도는 180 이하여야 합니다.")
    private Double startLongitude = -180.0;

    @Min(value = -90, message = "끝 위도는 -90 이상이어야 합니다.")
    @Max(value = 90, message = "끝 위도는 90 이하여야 합니다.")
    private Double endLatitude = 90.0;

    @Min(value = -180, message = "끝 경도는 -180 이상이어야 합니다.")
    @Max(value = 180, message = "끝 경도는 180 이하여야 합니다.")
    private Double endLongitude = 180.0;

    @AssertTrue(message = "시작 위도는 끝 위도보다 작아야 합니다.")
    public boolean isStartLatitudeLessThanEndLatitude() {
        return startLatitude <= endLatitude;
    }

    @AssertTrue(message = "시작 경도는 끝 경도보다 작아야 합니다.")
    public boolean isStartLongitudeLessThanEndLongitude() {
        return startLongitude <= endLongitude;
    }
}