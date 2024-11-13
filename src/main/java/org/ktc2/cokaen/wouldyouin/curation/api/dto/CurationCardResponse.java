package org.ktc2.cokaen.wouldyouin.curation.api.dto;

import java.util.List;
import lombok.Builder;
import org.ktc2.cokaen.wouldyouin.image.persist.CurationImage;
import org.ktc2.cokaen.wouldyouin.curation.persist.CurationCard;

@Builder
public class CurationCardResponse {

    private String subtitle;
    private String content;
    private List<String> imageUrls;

    public static CurationCardResponse from(CurationCard curationCard, List<String> imageUrls) {
        return CurationCardResponse.builder()
            .subtitle(curationCard.getSubtitle())
            .content(curationCard.getContent())
            .imageUrls(imageUrls)
            .build();
    }
}