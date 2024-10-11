package org.ktc2.cokaen.wouldyouin.curation.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.ktc2.cokaen.wouldyouin._common.persist.Area;
import org.ktc2.cokaen.wouldyouin.curation.persist.Curation;

@Getter
@AllArgsConstructor
public class CurationRequest {

    private final Long curatorId;

    private final Area area;

    private final String title;

    private final String content;

    private final String hashTag;

    private final Long eventId;

    public Curation toEntity() {
        return Curation.builder()
            .area(this.area)
            .title(this.title)
            .curatorId(this.curatorId)
            .eventId(this.eventId)
            .content(this.content)
            .hashTag(this.hashTag)
            .build();
    }
}
