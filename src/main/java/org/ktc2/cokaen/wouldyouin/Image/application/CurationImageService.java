package org.ktc2.cokaen.wouldyouin.Image.application;

import lombok.RequiredArgsConstructor;
import org.ktc2.cokaen.wouldyouin.Image.api.ImageDomain;
import org.ktc2.cokaen.wouldyouin.Image.api.dto.ImageRequest;
import org.ktc2.cokaen.wouldyouin.Image.persist.CurationImage;
import org.ktc2.cokaen.wouldyouin.Image.persist.CurationImageRepository;
import org.ktc2.cokaen.wouldyouin.Image.persist.ImageRepository;
import org.ktc2.cokaen.wouldyouin.curation.persist.CurationCard;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CurationImageService extends ImageService<CurationImage> {

    @Value("${image.upload.curation.child-path}")
    private String childPath;
    private final CurationImageRepository curationImageRepository;

    @Override
    public ImageRepository<CurationImage> getImageRepository() {
        return curationImageRepository;
    }

    @Override
    protected ImageDomain getImageDomain() {
        return ImageDomain.CURATION;
    }

    @Override
    protected String getChildPath() {
        return childPath;
    }

    @Override
    protected CurationImage toEntity(ImageRequest imageRequest) {
        return CurationImage.builder()
            .url(imageRequest.getUrl())
            .size(imageRequest.getSize())
            .build();
    }

    @Transactional
    public void setCuration(CurationImage image, CurationCard curationCard) {
        image.setCurationCard(curationCard);
    }
}