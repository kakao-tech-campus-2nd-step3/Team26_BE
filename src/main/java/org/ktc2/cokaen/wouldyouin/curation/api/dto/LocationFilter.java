package org.ktc2.cokaen.wouldyouin.curation.api.dto;

import lombok.Getter;

@Getter
public class LocationFilter {

    private Double startLatitude;
    private Double startLongitude;
    private Double endLatitude;
    private Double endLongitude;
}
