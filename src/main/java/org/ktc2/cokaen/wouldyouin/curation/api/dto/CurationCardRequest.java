package org.ktc2.cokaen.wouldyouin.curation.api.dto;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import java.util.List;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.ktc2.cokaen.wouldyouin.curation.persist.CurationCard;
import org.ktc2.cokaen.wouldyouin.image.persist.CurationCardImage;

@Getter
@Builder(toBuilder = true)
@EqualsAndHashCode
@ToString
public class CurationCardRequest {

    @NotEmpty(message = "부제목은 필수입니다.")
    private String subtitle;

    @NotBlank(message = "내용은 필수입니다.")
    @Size(min = 20, max = 1000, message = "내용은 20자 이상 1000자 이하입니다.")
    private String content;

    private List<Long> imageIds;

    @AssertTrue(message = "이미지는 최대 5개까지 등록할 수 있습니다.")
    public boolean isImageSizeValid() {
        return imageIds == null || imageIds.size() <= 5;
    }

    public CurationCard toEntity(List<CurationCardImage> images) {
        return CurationCard.builder()
            .subtitle(this.subtitle)
            .content(this.content)
            .images(images)
            .build();
    }
}