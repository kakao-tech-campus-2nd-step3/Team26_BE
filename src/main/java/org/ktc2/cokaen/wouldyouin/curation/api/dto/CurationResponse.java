package org.ktc2.cokaen.wouldyouin.curation.api.dto;

import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.ktc2.cokaen.wouldyouin._common.vo.Area;
import org.ktc2.cokaen.wouldyouin.curation.persist.Curation;
import org.ktc2.cokaen.wouldyouin.event.api.dto.relationResonse.CurationEventResponse;
import org.ktc2.cokaen.wouldyouin.member.api.dto.relationResponse.CurationCuratorResponse;

@Builder
@Getter
@EqualsAndHashCode
public class CurationResponse {

    private final Long id;
    private final CurationCuratorResponse curator;
    private final String title;
    private final String content;
    private final List<CurationCardResponse> curationCards;
    private final Area area;
    private final List<String> hashtags;
    private final List<CurationEventResponse> eventsInfo;
    private final LocalDateTime createdTime;
    private final LocalDateTime modifiedDate;

    public static CurationResponse from(Curation curation) {
        return CurationResponse.builder()
            .id(curation.getId())
            .curator(CurationCuratorResponse.from(curation.getCurator()))
            .title(curation.getTitle())
            .content(curation.getContent())
            .curationCards(curation.getCurationCards().stream()
                .map(CurationCardResponse::from).toList())
            .area(curation.getArea())
            .hashtags(curation.getHashtags())
            .eventsInfo(curation.getEvents().stream()
                .map(CurationEventResponse::from).toList())
            .createdTime(curation.getCreatedDate())
            .modifiedDate(curation.getModifiedDate())
            .build();
    }
}