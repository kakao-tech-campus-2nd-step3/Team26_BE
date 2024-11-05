package org.ktc2.cokaen.wouldyouin.Image.application;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.ktc2.cokaen.wouldyouin.Image.api.ImageDomain;
import org.ktc2.cokaen.wouldyouin.Image.api.dto.ImageRequest;
import org.ktc2.cokaen.wouldyouin.Image.api.dto.ImageResponse;
import org.ktc2.cokaen.wouldyouin.Image.persist.Image;
import org.ktc2.cokaen.wouldyouin.Image.persist.ImageRepository;
import org.ktc2.cokaen.wouldyouin._common.exception.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public abstract class ImageService<T extends Image> {

    @Autowired
    protected ImageStorage imageStorage;

    protected abstract ImageRepository<T> getImageRepository();

    protected abstract ImageDomain getImageDomain();

    protected abstract String getSubPath();

    protected abstract T toEntity(ImageRequest imageRequest);

    public T getById(Long id) {
        return getImageRepository().findById(id)
            .orElseThrow(() -> new EntityNotFoundException(getImageDomain().name() + " Image"));
    }

    protected ImageResponse create(ImageRequest imageRequest) {
        return ImageResponse.from(getImageRepository().save(toEntity(imageRequest)));
    }

    protected void delete(Long id) {
        getImageRepository().findById(id)
            .orElseThrow(() -> new EntityNotFoundException(getImageDomain().name() + " Image"));
        getImageRepository().deleteById(id);
    }

    @Transactional
    public List<ImageResponse> saveAndCreateImages(List<MultipartFile> images) {
        return images.stream()
            .map(image -> {
                String path = imageStorage.save(image, getSubPath());
                return create(ImageRequest.of(path, image.getSize(), ImageStorage.getExtension(image)));
            })
            .toList();
    }

    @Transactional
    public void deleteAndDelete(Long id) {
        String url = getImageRepository().findById(id)
            .orElseThrow(() -> new EntityNotFoundException(getImageDomain().name() + " Image")).getUrl();
        delete(id);
        imageStorage.delete(url);
    }
}