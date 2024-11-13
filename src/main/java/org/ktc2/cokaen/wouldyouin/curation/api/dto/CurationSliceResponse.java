package org.ktc2.cokaen.wouldyouin.curation.api.dto;

import java.util.List;
import lombok.Builder;
import lombok.Getter;
import org.ktc2.cokaen.wouldyouin._common.api.SliceInfo;
import org.ktc2.cokaen.wouldyouin.curation.persist.Curation;
import org.springframework.data.domain.Slice;

@Getter
@Builder
public class CurationSliceResponse {

    private final List<CurationResponse> curations;
    private final SliceInfo sliceInfo;

    public static CurationSliceResponse from(List<CurationResponse> curations, int size, Long lastId) {
        return CurationSliceResponse.builder()
            .curations(curations)
            .sliceInfo(SliceInfo.builder()
                .sliceSize(size)
                .lastId(lastId)
                .build())
            .build();
    }
}