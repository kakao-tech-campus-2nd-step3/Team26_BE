package org.ktc2.cokaen.wouldyouin.curation.api.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.List;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.ktc2.cokaen.wouldyouin._common.vo.Area;
import org.ktc2.cokaen.wouldyouin.curation.persist.Curation;
import org.ktc2.cokaen.wouldyouin.curation.persist.CurationCard;
import org.ktc2.cokaen.wouldyouin.event.persist.Event;
import org.ktc2.cokaen.wouldyouin.member.persist.Curator;

@Getter
@Builder(toBuilder = true)
@EqualsAndHashCode
@ToString
public class CurationCreateRequest {

    @NotEmpty(message = "제목은 필수입니다.")
    private String title;

    @NotBlank(message = "내용은 필수입니다.")
    @Size(min = 20, max = 1000, message = "내용은 20자 이상 1000자 이하입니다.")
    private String content;

    @Valid
    private List<CurationCardRequest> curationCards;

    @NotNull(message = "지역은 필수입니다.")
    private Area area;

    private List<String> hashtags;

    private List<Long> eventIds;

    @AssertTrue(message = "큐레이션 카드의 개수는 1개 이상 10개 이하이어야 합니다.")
    private boolean isCurationCardsSizeValid() {
        return curationCards != null && 1 <= this.curationCards.size()
            && this.curationCards.size() <= 10;
    }

    public Curation toEntity(Curator curator, List<CurationCard> curationCards,
        List<Event> events, String thumbnailUrl) {
        return Curation.builder()
            .curator(curator)
            .title(this.title)
            .content(this.content)
            .curationCards(curationCards)
            .area(this.area)
            .hashtags(this.hashtags)
            .events(events)
            .thumbnailUrl(thumbnailUrl)
            .build();
    }
}
