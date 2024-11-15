package org.ktc2.cokaen.wouldyouin.event.api.dto.relationResonse;

import java.time.LocalDateTime;
import java.util.Optional;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.ktc2.cokaen.wouldyouin._common.vo.Location;
import org.ktc2.cokaen.wouldyouin.event.persist.Event;
import org.ktc2.cokaen.wouldyouin.member.persist.BaseMember;

@Getter
@Builder
@EqualsAndHashCode
@ToString
public class CurationEventResponse {

    private Long eventId;
    private String title;
    private Location location;
    private LocalDateTime startTime;
    private String thumbnailImageUrl;
    private String hostProfileImageUrl;
    private String hostNickname;

    public static CurationEventResponse from(Event event) {
        return CurationEventResponse.builder()
            .eventId(event.getId())
            .title(event.getTitle())
            .location(event.getLocation())
            .startTime(event.getStartTime())
            .thumbnailImageUrl(event.getThumbnailUrl())
            .hostProfileImageUrl(Optional.of(event)
                .map(Event::getHost)
                .map(BaseMember::getProfileImageUrl)
                .orElse(""))
            .hostNickname(event.getHost().getNickname())
            .build();
    }
}