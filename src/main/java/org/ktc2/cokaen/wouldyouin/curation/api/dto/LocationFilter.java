package org.ktc2.cokaen.wouldyouin.curation.api.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class LocationFilter {

    private Double startLatitude = -90.0;
    private Double startLongitude = -180.0;
    private Double endLatitude = 90.0;
    private Double endLongitude = 180.0;
}