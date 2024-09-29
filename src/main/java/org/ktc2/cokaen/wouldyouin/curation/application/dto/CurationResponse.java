package org.ktc2.cokaen.wouldyouin.curation.application.dto;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import org.ktc2.cokaen.wouldyouin._common.persist.Area;
import org.ktc2.cokaen.wouldyouin.curation.persist.Curation;

@AllArgsConstructor
public class CurationResponse {

    private final Long curationId;

    private final Long curatorId;

    private final String title;

    private final String content;

    private final Area area;

    private final LocalDateTime createdTime;

    private final String hashTag;

    private final Long eventId;


    public static CurationResponse from(Curation curation) {
        return new CurationResponse(curation.getId(), curation.getCuratorId(), curation.getTitle(),
            curation.getContent(), curation.getArea(), curation.getCreatedTime(),
            curation.getHashTag(), curation.getEventId());
    }
}
