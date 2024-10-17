package org.ktc2.cokaen.wouldyouin.Image.application;

import lombok.RequiredArgsConstructor;
import org.ktc2.cokaen.wouldyouin.Image.api.ImageDomain;
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

    // 구체 이미지 타입을 반환하는 로직을 하위 서비스가 위임하도록 합니다.
    protected abstract T toEntity(String path, Long size, String extension);

    @Transactional(readOnly = true)
    protected String getNameById(Long id) {
        T target = getImageRepository().findById(id).orElseThrow(RuntimeException::new);
        return target.getUrl();
    }

    @Transactional
    protected void delete(Long id) {
        getImageRepository().findById(id).orElseThrow(RuntimeException::new);
        getImageRepository().deleteById(id);
    }

    @Transactional
    public ImageResponse saveAndCreate(MultipartFile image) {
        String path = imageStorage.save(image, getImageType());
        //ImageRequest는 서비스 내에서만 이용하니까 그냥 파라미터로 전달하면 어떨까요?, 즉 imageRequest를 삭제합니다.
        return ImageResponse.from(
            getImageRepository().save(toEntity(
                path, image.getSize(), image.getContentType())
            )
        );
    }

    @Transactional
    public void deleteAndDelete(Long id) {
        String url = getImageRepository().findById(id).orElseThrow(RuntimeException::new).getUrl();
        delete(id);
        imageStorage.delete(url);
    }
}