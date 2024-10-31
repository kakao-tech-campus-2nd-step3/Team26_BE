package org.ktc2.cokaen.wouldyouin.event.api.dto.relationResonse;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import org.ktc2.cokaen.wouldyouin._common.persist.Location;

@Getter
@Builder
public class CurationEventResponse {

    private Long id;
    private String title;
    private Location location;
    private LocalDateTime startTime;
    private String thumbnailImageUrl;
    private String hostProfileImageUrl;
    private String hostNickname;
}