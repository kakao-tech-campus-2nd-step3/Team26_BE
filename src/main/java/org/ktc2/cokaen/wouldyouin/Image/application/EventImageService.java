package org.ktc2.cokaen.wouldyouin.Image.application;

import lombok.RequiredArgsConstructor;
import org.ktc2.cokaen.wouldyouin.Image.api.ImageDomain;
import org.ktc2.cokaen.wouldyouin.Image.persist.EventImage;
import org.ktc2.cokaen.wouldyouin.Image.persist.EventImageRepository;
import org.ktc2.cokaen.wouldyouin.Image.persist.ImageRepository;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class EventImageService extends ImageService<EventImage> {

    private final EventImageRepository eventImageRepository;

    @Override
    public ImageDomain getImageType() {
        return ImageDomain.EVENT;
    }

    @Override
    public ImageRepository<EventImage> getImageRepository() {
        return eventImageRepository;
    }

    @Override
    public void delete(Long id) {
        eventImageRepository.findById(id).orElseThrow(RuntimeException::new);
        eventImageRepository.deleteById(id);
    }
}