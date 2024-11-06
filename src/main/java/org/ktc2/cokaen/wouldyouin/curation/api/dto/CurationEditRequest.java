package org.ktc2.cokaen.wouldyouin.curation.api.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import org.ktc2.cokaen.wouldyouin._common.vo.Area;

@Getter
@Builder(toBuilder = true)
public class CurationEditRequest {

    @NotEmpty(message = "제목은 필수입니다.")
    private String title;

    private String content;

    private List<CurationCardRequest> curationCards;

    @NotNull(message = "지역은 필수입니다.")
    private Area area;

    private List<String> hashTag;

    private List<Long> eventIds;
}