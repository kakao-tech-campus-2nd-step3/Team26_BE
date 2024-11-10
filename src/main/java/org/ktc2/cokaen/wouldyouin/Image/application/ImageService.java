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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public abstract class ImageService<T extends Image> {

    @Autowired
    protected ImageStorageService imageStorageService;

    @Value("${spring.wouldyouin-domain-name}")
    private String domainName;

    protected abstract ImageRepository<T> getImageRepository();

    protected abstract ImageDomain getImageDomain();

    protected abstract String getChildPath();

    protected abstract T toEntity(ImageRequest imageRequest);

    public T getById(Long id) {
        return getImageRepository().findById(id)
            .orElseThrow(() -> new EntityNotFoundException(getImageDomain().name() + " 이미지를 찾을 수 없습니다."));
    }

    protected ImageResponse create(ImageRequest imageRequest) {
        return ImageResponse.from(getImageRepository().save(toEntity(imageRequest)), domainName);
    }

    protected void delete(Long id) {
        getById(id);
        getImageRepository().deleteById(id);
    }

    @Transactional
    public List<ImageResponse> saveImages(List<MultipartFile> images) {
        return images.stream()
            .map(image -> create(imageStorageService.saveToDirectory(image, getChildPath())))
            .toList();
    }

    @Transactional
    public void deleteAndDelete(Long id) {
        T image = getById(id);
        delete(id);
        imageStorageService.delete(image.getUrl());
    }
}