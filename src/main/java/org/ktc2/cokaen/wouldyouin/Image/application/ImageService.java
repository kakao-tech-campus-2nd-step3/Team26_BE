package org.ktc2.cokaen.wouldyouin.Image.application;

import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.ktc2.cokaen.wouldyouin.Image.api.ImageDomain;
import org.ktc2.cokaen.wouldyouin.Image.api.dto.ImageRequest;
import org.ktc2.cokaen.wouldyouin.Image.api.dto.ImageResponse;
import org.ktc2.cokaen.wouldyouin.Image.persist.Image;
import org.ktc2.cokaen.wouldyouin.Image.persist.ImageRepository;
import org.ktc2.cokaen.wouldyouin._common.api.EntityGettable;
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

    protected abstract T mapToEntityFrom(ImageRequest imageRequest);

    protected ImageResponse create(ImageRequest imageRequest) {
        return ImageResponse.from(getImageRepository().save(mapToEntityFrom(imageRequest)));
    }

    protected void delete(Long id) {
        getImageRepository().findById(id).orElseThrow(RuntimeException::new);
        getImageRepository().deleteById(id);
    }

    @Transactional
    public List<ImageResponse> saveAndCreateImages(List<MultipartFile> images) {
        List<String> paths = images.stream().map(image -> imageStorage.save(image, getSubPath())).toList();
        return paths.stream()
            .map(path -> create(ImageRequest.of(path, images.get(paths.indexOf(path)).getSize())))
            .collect(Collectors.toList());
    }

    @Transactional
    public void deleteAndDelete(Long id) {
        String url = getImageRepository().findById(id).orElseThrow(RuntimeException::new).getUrl();
        delete(id);
        imageStorage.delete(url);
    }
}