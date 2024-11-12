package org.ktc2.cokaen.wouldyouin.Image.application;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.ktc2.cokaen.wouldyouin.Image.api.ImageDomain;
import org.ktc2.cokaen.wouldyouin.Image.api.dto.ImageRequest;
import org.ktc2.cokaen.wouldyouin.Image.api.dto.ImageResponse;
import org.ktc2.cokaen.wouldyouin.Image.persist.Image;
import org.ktc2.cokaen.wouldyouin.Image.persist.ImageRepository;
import org.ktc2.cokaen.wouldyouin._common.exception.EntityNotFoundException;
import org.ktc2.cokaen.wouldyouin._common.util.UriUtil;
import org.ktc2.cokaen.wouldyouin.auth.MemberIdentifier;
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

    @Value("${image.api-url}")
    private String parentPath;

    protected abstract ImageRepository<T> getImageRepository();

    protected abstract ImageDomain getImageDomain();

    protected abstract String getChildPath();

    protected abstract T toEntity(ImageRequest imageRequest);

    protected abstract void validateMemberId(MemberIdentifier identifier, T image);

    @Transactional
    public List<ImageResponse> saveImages(List<MultipartFile> images) {
        return images.stream()
            .map(image -> create(imageStorageService.saveToDirectory(image, getChildPath())))
            .toList();
    }

    // TODO: delete 이미지 바꿔야함
    @Transactional
    public void deleteImage(MemberIdentifier identifier, Long imageId) {
        T image = getById(imageId);
        validateMemberId(identifier, image);
        delete(imageId);
        imageStorageService.delete(getChildPath(), image.getName());
    }

    public T getById(Long id) {
        return getImageRepository().findById(id)
            .orElseThrow(() -> new EntityNotFoundException(getImageDomain().name() + " 이미지를 찾을 수 없습니다."));
    }

    protected ImageResponse create(ImageRequest imageRequest) {
        T image = toEntity(imageRequest);
        return ImageResponse.from(getImageRepository().save(image), getImageUrl(image));
    }

    public String getImageUrl(T image) {
        return UriUtil.assembleFullUrl(parentPath, getChildPath(), image.getName());
    }

    protected void delete(Long id) {
        getById(id);
        getImageRepository().deleteById(id);
    }
}