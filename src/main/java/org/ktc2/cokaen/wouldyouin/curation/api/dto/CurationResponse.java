package org.ktc2.cokaen.wouldyouin.curation.api.dto;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.ktc2.cokaen.wouldyouin._common.persist.Area;
import org.ktc2.cokaen.wouldyouin.curation.persist.Curation;
import org.ktc2.cokaen.wouldyouin.event.api.dto.relationResonse.CurationEventResponse;
import org.ktc2.cokaen.wouldyouin.event.persist.Event;
import org.ktc2.cokaen.wouldyouin.member.application.dto.relationResponse.CurationCuratorResponse;
import org.ktc2.cokaen.wouldyouin.member.persist.Curator;

@Builder
@Getter
public class CurationResponse {

    private final Long id;
    private final CurationCuratorResponse curator;
    private final String title;
    private final String content;
    private final Area area;
    private final LocalDateTime createdTime;
    private final String hashTag;
    private final CurationEventResponse event;


    public static CurationResponse from(Curation curation) {
        Curator curator = curation.getCurator();
        Event eventInCuration = curation.getEvent();
        return CurationResponse.builder()
            .id(curation.getId())
            .curator(CurationCuratorResponse.builder()
                .nickname(curator.getNickname())
                .email(curator.getEmail())
                .phone(curator.getPhone())
                .profileImageUrl(curator.getProfileImageUrl())
                .intro(curator.getIntro())
                .likes(curator.getLikes())
                .hashtags(curator.getHashTagList())
                .build())
            .title(curation.getTitle())
            .content(curation.getContent())
            .area(curation.getArea())
            .createdTime(curation.getCreatedTime())
            .hashTag(curation.getHashTag())
            .event(CurationEventResponse.builder()
                .id(eventInCuration.getId())
                .title(eventInCuration.getTitle())
                .location(eventInCuration.getLocation())
                .startTime(eventInCuration.getStartTime())
//                .mainImage(eventInCuration.getMainImage())
                .build())
            .build();

    }
}
