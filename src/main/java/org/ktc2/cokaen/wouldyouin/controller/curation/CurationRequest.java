package org.ktc2.cokaen.wouldyouin.controller.curation;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.ktc2.cokaen.wouldyouin.domain.Area;
import org.ktc2.cokaen.wouldyouin.domain.Curation;

@Getter
@AllArgsConstructor
public class CurationRequest {

    private final Long curatorId;

    private final Area area;

    private final String title;

    private final String content;

    private final String hashTag;

    private final Long eventId;


}
