package org.ktc2.cokaen.wouldyouin.controller.curation;

import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.ktc2.cokaen.wouldyouin.domain.Area;

@Getter
@AllArgsConstructor
public class CurationRequest {

    private final UUID curatorId;

    private final Area area;

    private final String title;

    private final String content;

    private final String hashTag;

    private final UUID eventId;

}
