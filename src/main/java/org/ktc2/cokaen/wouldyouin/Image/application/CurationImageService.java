package org.ktc2.cokaen.wouldyouin.Image.application;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.ktc2.cokaen.wouldyouin.Image.api.ImageDomain;
import org.ktc2.cokaen.wouldyouin.Image.api.dto.ImageRequest;
import org.ktc2.cokaen.wouldyouin.Image.persist.CurationImage;
import org.ktc2.cokaen.wouldyouin.Image.persist.CurationImageRepository;
import org.ktc2.cokaen.wouldyouin.Image.persist.ImageRepository;
import org.ktc2.cokaen.wouldyouin._common.api.EntityGettable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CurationImageService extends ImageService<CurationImage> implements EntityGettable<List<Long>, List<CurationImage>> {

    private final CurationImageRepository curationImageRepository;

    @Value("${image.upload.curation.sub-path}")
    private String subPath;

    @Override
    public ImageRepository<CurationImage> getImageRepository() {
        return curationImageRepository;
    }

    @Override
    protected String getSubPath() {
        return subPath;
    }

    @Override
    protected ImageDomain getImageDomain() {
        return ImageDomain.CURATION;
    }

    @Override
    protected CurationImage mapToEntityFrom(ImageRequest imageRequest) {
        return CurationImage.builder()
            .name(imageRequest.getUrl())
            .size(imageRequest.getSize())
            .build();
    }

    @Override
    public List<CurationImage> getByIdOrThrow(List<Long> ids) throws RuntimeException {
        return ids.stream().map(id -> curationImageRepository.findById(id).orElseThrow(RuntimeException::new)).toList();
    }
}