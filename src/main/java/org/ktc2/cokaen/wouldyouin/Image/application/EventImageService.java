package org.ktc2.cokaen.wouldyouin.Image.application;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.ktc2.cokaen.wouldyouin.Image.api.ImageDomain;
import org.ktc2.cokaen.wouldyouin.Image.api.dto.ImageRequest;
import org.ktc2.cokaen.wouldyouin.Image.persist.EventImage;
import org.ktc2.cokaen.wouldyouin.Image.persist.EventImageRepository;
import org.ktc2.cokaen.wouldyouin.Image.persist.ImageRepository;
import org.ktc2.cokaen.wouldyouin._common.api.EntityGettable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class EventImageService extends ImageService<EventImage> implements EntityGettable<List<Long>, List<EventImage>> {

    private final EventImageRepository eventImageRepository;

    @Value("${image.upload.event.sub-path}")
    private String subPath;

    @Override
    public ImageRepository<EventImage> getImageRepository() {
        return eventImageRepository;
    }

    @Override
    protected String getSubPath() {
        return subPath;
    }

    @Override
    protected ImageDomain getImageDomain() {
        return ImageDomain.EVENT;
    }

    @Override
    protected EventImage mapToEntityFrom(ImageRequest imageRequest) {
        return EventImage.builder().name(imageRequest.getUrl()).size(imageRequest.getSize()).build();
    }

    @Override
    public void delete(Long id) {
        eventImageRepository.findById(id).orElseThrow(RuntimeException::new);
        eventImageRepository.deleteById(id);
    }

    @Override
    public List<EventImage> getByIdOrThrow(List<Long> ids) throws RuntimeException {
        return ids.stream().map(id -> eventImageRepository.findById(id).orElseThrow(RuntimeException::new)).toList();
    }
}