package org.ktc2.cokaen.wouldyouin.Image.application;

import lombok.RequiredArgsConstructor;
import org.ktc2.cokaen.wouldyouin.Image.api.ImageDomain;
import org.ktc2.cokaen.wouldyouin.Image.persist.CurationImage;
import org.ktc2.cokaen.wouldyouin.Image.persist.CurationImageRepository;
import org.ktc2.cokaen.wouldyouin.Image.persist.ImageRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CurationImageService extends ImageService<CurationImage> {

    private final CurationImageRepository curationImageRepository;

    @Override
    public ImageDomain getImageType() {
        return ImageDomain.CURATION;
    }

    @Override
    public ImageRepository<CurationImage> getImageRepository() {
        return curationImageRepository;
    }

    @Override
    protected CurationImage toEntity(String path, Long size, String extension) {
        return CurationImage.builder()
            .name(path)
            .size(size)
            .extension(extension)
            .build();
    }
}
