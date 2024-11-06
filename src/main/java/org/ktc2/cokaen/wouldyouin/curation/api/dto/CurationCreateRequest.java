package org.ktc2.cokaen.wouldyouin.curation.api.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import org.ktc2.cokaen.wouldyouin._common.vo.Area;
import org.ktc2.cokaen.wouldyouin.curation.persist.Curation;
import org.ktc2.cokaen.wouldyouin.curation.persist.CurationCard;
import org.ktc2.cokaen.wouldyouin.event.persist.Event;
import org.ktc2.cokaen.wouldyouin.member.persist.Curator;

@Getter
@Builder(toBuilder = true)
public class CurationCreateRequest {

    @NotEmpty(message = "제목은 필수입니다.")
    private String title;

    private String content;

    @Valid
    private List<CurationCardRequest> curationCards;

    @NotNull(message = "지역은 필수입니다.")
    private Area area;

    private List<String> hashTag;

    private List<Long> eventIds;

    public Curation toEntity(Curator curator, List<CurationCard> curationCards, List<Event> events) {
        return Curation.builder()
            .curator(curator)
            .title(this.title)
            .content(this.content)
            .curationCards(curationCards)
            .area(this.area)
            .hashTag(this.hashTag)
            .events(events)
            .build();
    }
}
