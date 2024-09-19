package org.ktc2.cokaen.wouldyouin.controller.curation;

import java.time.LocalDateTime;
import java.util.UUID;
import lombok.AllArgsConstructor;
import org.ktc2.cokaen.wouldyouin.domain.Area;
import org.ktc2.cokaen.wouldyouin.domain.Curation;

@AllArgsConstructor
public class CurationResponse {

    private final UUID curationId;

    private final UUID curatorId;

    private final String title;

    private final String content;

    private final Area area;

    private final LocalDateTime createdTime;

    private final String hashTag;

    private final UUID eventId;


    public static CurationResponse toCurationResponse(Curation curation) {
        return new CurationResponse(curation.getId(), curation.getCuratorId(), curation.getTitle(),
            curation.getContent(), curation.getArea(), curation.getCreatedTime(),
            curation.getHashTag(), curation.getEventId());
    }
}
