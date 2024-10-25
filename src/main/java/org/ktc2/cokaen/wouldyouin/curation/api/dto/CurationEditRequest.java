package org.ktc2.cokaen.wouldyouin.curation.api.dto;

import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import org.ktc2.cokaen.wouldyouin._common.persist.Area;
import org.ktc2.cokaen.wouldyouin.curation.persist.Curation;

@Getter
@Builder(toBuilder = true)
public class CurationEditRequest {

    private String title;
    private String content;
    private Area area;
    private LocalDateTime createdTime;
    private String hashTag;
    private Long eventId;
    private List<Long> imageIds;

    public Curation toEntity() {
        return Curation.builder()
            .title(this.title)
            .content(this.content)
            .area(this.area)
            .createdTime(this.createdTime)
            .hashTag(this.hashTag)
            .build();
    }
}
