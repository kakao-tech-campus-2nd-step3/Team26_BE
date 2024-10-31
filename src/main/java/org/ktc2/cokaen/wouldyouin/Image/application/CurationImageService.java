package org.ktc2.cokaen.wouldyouin.Image.application;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.ktc2.cokaen.wouldyouin.Image.api.ImageDomain;
import org.ktc2.cokaen.wouldyouin.Image.api.dto.ImageRequest;
import org.ktc2.cokaen.wouldyouin.Image.persist.CurationImage;
import org.ktc2.cokaen.wouldyouin.Image.persist.CurationImageRepository;
import org.ktc2.cokaen.wouldyouin.Image.persist.ImageRepository;
import org.ktc2.cokaen.wouldyouin._common.exception.EntityParamIsNullException;
import org.ktc2.cokaen.wouldyouin.curation.persist.CurationCard;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CurationImageService extends ImageService<CurationImage> {

    private final CurationImageRepository curationImageRepository;

    @Value("${image.upload.curation.sub-path}")
    private String subPath;

    @Override
    public ImageRepository<CurationImage> getImageRepository() {
        return curationImageRepository;
    }

    @Override
    protected ImageDomain getImageDomain() {
        return ImageDomain.CURATION;
    }

    @Override
    protected String getSubPath() {
        return subPath;
    }

    @Override
    protected CurationImage toEntity(ImageRequest imageRequest) {
        return CurationImage.builder()
            .name(imageRequest.getUrl())
            .size(imageRequest.getSize())
            .build();
    }

    @Transactional
    public void setCuration(CurationImage image, CurationCard curationCard) {
        Optional.ofNullable(image).orElseThrow(() -> new EntityParamIsNullException(getImageDomain().name() + " image"));
        Optional.ofNullable(curationCard).orElseThrow(() -> new EntityParamIsNullException("curationCard"));
        image.setCurationCard(curationCard);
    }
}