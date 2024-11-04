package org.ktc2.cokaen.wouldyouin.curation.api.dto;

import java.util.List;
import lombok.Builder;
import org.ktc2.cokaen.wouldyouin._common.api.SliceInfo;

@Builder
public class CurationSliceResponse {

    private final List<CurationResponse> curations;
    private final SliceInfo slice;

    public static CurationSliceResponse of(List<CurationResponse> curationResponses, int sliceSize, long lastId) {
        return CurationSliceResponse.builder()
            .curations(curationResponses)
            .slice(SliceInfo.builder()
                .sliceSize(sliceSize)
                .lastId(lastId)
                .build())
            .build();
    }
}