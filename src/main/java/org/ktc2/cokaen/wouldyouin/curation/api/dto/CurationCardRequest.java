package org.ktc2.cokaen.wouldyouin.curation.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import org.ktc2.cokaen.wouldyouin.Image.persist.CurationImage;
import org.ktc2.cokaen.wouldyouin.curation.persist.CurationCard;

@Getter
@Builder(toBuilder = true)
public class CurationCardRequest {

    @NotEmpty(message = "부제목은 필수입니다.")
    private String subtitle;

    @NotBlank(message = "내용은 필수입니다.")
    @Size(min = 20, max = 1000, message = "내용은 20자 이상 1000자 이하입니다.")
    private String content;

    private List<Long> imageIds;

    public CurationCard toEntity(List<CurationImage> images) {
        return CurationCard.builder()
            .subtitle(this.subtitle)
            .content(this.content)
            .images(images)
            .build();
    }
}