package org.ktc2.cokaen.wouldyouin.Image.application;

import lombok.RequiredArgsConstructor;
import org.ktc2.cokaen.wouldyouin.Image.api.ImageDomain;
import org.ktc2.cokaen.wouldyouin.Image.api.ImageRequest;
import org.ktc2.cokaen.wouldyouin.Image.api.ImageResponse;
import org.ktc2.cokaen.wouldyouin.Image.persist.Image;
import org.ktc2.cokaen.wouldyouin.Image.persist.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public abstract class ImageService<T extends Image> {

    @Autowired
    protected ImageStorage imageStorage;

    protected abstract ImageDomain getImageType();

    protected abstract ImageRepository<T> getImageRepository();

    @Transactional(readOnly = true)
    protected String getNameById(Long id) {
        T target = getImageRepository().findById(id).orElseThrow(RuntimeException::new);
        return target.getUrl();
    }

    @Transactional
    protected ImageResponse create(ImageRequest imageRequest) {
        try {
            T imageEntity = imageRequest.toEntity(getImageType().getClassType());
            return ImageResponse.from(getImageRepository().save(imageEntity));
        } catch (Exception e) {
            throw new RuntimeException("failed to create image entity");
        }
    }

    @Transactional
    protected void delete(Long id) {
        getImageRepository().findById(id).orElseThrow(RuntimeException::new);
        getImageRepository().deleteById(id);
    }

    @Transactional
    public ImageResponse saveAndCreate(MultipartFile image) {
        String path = imageStorage.save(image, getImageType());
        return create(ImageRequest.of(path, image.getSize(), image.getContentType()));
    }

    @Transactional
    public void deleteAndDelete(Long id) {
        String url = getImageRepository().findById(id).orElseThrow(RuntimeException::new).getUrl();
        delete(id);
        imageStorage.delete(url);
    }
}