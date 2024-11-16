package org.ktc2.cokaen.wouldyouin.image.application;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.ktc2.cokaen.wouldyouin._common.exception.EntityNotFoundException;
import org.ktc2.cokaen.wouldyouin._common.util.UriUtil;
import org.ktc2.cokaen.wouldyouin.auth.MemberIdentifier;
import org.ktc2.cokaen.wouldyouin.image.api.ImageDomain;
import org.ktc2.cokaen.wouldyouin.image.api.dto.ImageRequest;
import org.ktc2.cokaen.wouldyouin.image.api.dto.ImageResponse;
import org.ktc2.cokaen.wouldyouin.image.persist.Image;
import org.ktc2.cokaen.wouldyouin.image.persist.ImageRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public abstract class ImageService<T extends Image> {

    protected ImageStorageService imageStorageService;

    @Value("${image.api-url}")
    private String imageApiHeader;

    protected abstract ImageRepository<T> getImageRepository();

    protected abstract ImageDomain getImageDomain();

    protected abstract String getChildPath();

    protected abstract T mapToEntityFrom(ImageRequest imageRequest);

    protected abstract void validateMemberId(MemberIdentifier identifier, T image);

    @Transactional(readOnly = true)
    public T getById(Long id) {
        return getImageRepository().findById(id)
            .orElseThrow(
                () -> new EntityNotFoundException(getImageDomain().name() + " 이미지를 찾을 수 없습니다."));
    }

    @Transactional
    public List<ImageResponse> saveImages(List<MultipartFile> images) {
        return images.stream()
            .map(image -> create(imageStorageService.saveToDirectory(image, getChildPath())))
            .toList();
    }

    @Transactional
    public void deleteImage(MemberIdentifier identifier, Long imageId) {
        T image = getById(imageId);
        validateMemberId(identifier, image);
        delete(imageId);
        imageStorageService.delete(getChildPath(), image.getName());
    }

    protected ImageResponse create(ImageRequest imageRequest) {
        T image = mapToEntityFrom(imageRequest);
        return ImageResponse.from(getImageRepository().save(image), getImageUrl(image));
    }

    protected void delete(Long id) {
        getById(id);
        getImageRepository().deleteById(id);
    }

    public String createThumbnail(String fileName) {
        return imageStorageService.createThumbnailImage(imageApiHeader, getChildPath(), fileName);
    }

    public String getImageUrl(T image) {
        return UriUtil.assembleFullUrl(imageApiHeader, getChildPath(), image.getName());
    }
}