package org.ktc2.cokaen.wouldyouin.curation.api.dto;

import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import org.ktc2.cokaen.wouldyouin.Image.persist.CurationImage;
import org.ktc2.cokaen.wouldyouin._common.persist.Area;
import org.ktc2.cokaen.wouldyouin.curation.persist.Curation;
import org.ktc2.cokaen.wouldyouin.curation.persist.CurationCard;
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
    private final List<CurationCardResponse> curationCards;
    private final Area area;
    private final List<String> hashTag;
    private final List<CurationEventResponse> eventsInfo;
    private LocalDateTime modifiedDate;
    private final LocalDateTime createdTime;

    public static CurationResponse from(Curation curation) {
        Curator curator = curation.getCurator();
        List<CurationCard> curationCards = curation.getCurationCards();
        List<Event> events = curation.getEvents();

        return CurationResponse.builder()
            .id(curation.getId())
            .curator(
                CurationCuratorResponse.builder()
                    .nickname(curator.getNickname())
                    .email(curator.getEmail())
                    .phone(curator.getPhone())
                    .profileImageUrl(curator.getProfileImageUrl())
                    .intro(curator.getIntro())
                    .likes(curator.getLikes())
                    .hashtags(curator.getHashTagList())
                    .build()
            )
            .title(curation.getTitle())
            .content(curation.getContent())
            .curationCards(
                curationCards.stream()
                    .map(card -> CurationCardResponse.builder()
                        .subtitle(card.getSubtitle())
                        .content(card.getContent())
                        .imageUrls(card.getCurationImages().stream().map(CurationImage::getUrl).toList())
                        .build()
                    ).toList()
            )
            .area(curation.getArea())
            .hashTag(curation.getHashTag())
            .eventsInfo(
                events.stream()
                    .map(event ->
                        CurationEventResponse.builder()
                            .id(event.getId())
                            .title(event.getTitle())
                            .location(event.getLocation())
                            .startTime(event.getStartTime())
                            .thumbnailImageUrl(event.getThumbnailUrl())
                            .hostProfileImageUrl(event.getHost().getProfileImageUrl())
                            .hostNickname(event.getHost().getNickname())
                            .build()
                    ).toList()
            )
            .createdTime(curation.getCreatedDate())
            .modifiedDate(curation.getModifiedDate())
            .build();
    }
}