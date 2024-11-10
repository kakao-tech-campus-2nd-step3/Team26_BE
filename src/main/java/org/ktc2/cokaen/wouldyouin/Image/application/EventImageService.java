package org.ktc2.cokaen.wouldyouin.Image.application;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.ktc2.cokaen.wouldyouin.Image.api.ImageDomain;
import org.ktc2.cokaen.wouldyouin.Image.api.dto.ImageRequest;
import org.ktc2.cokaen.wouldyouin.Image.persist.EventImage;
import org.ktc2.cokaen.wouldyouin.Image.persist.EventImageRepository;
import org.ktc2.cokaen.wouldyouin.Image.persist.ImageRepository;
import org.ktc2.cokaen.wouldyouin._common.exception.EntityParamIsNullException;
import org.ktc2.cokaen.wouldyouin.event.persist.Event;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class EventImageService extends ImageService<EventImage> {

    private final EventImageRepository eventImageRepository;

    @Value("${image.upload.event.sub-path}")
    private String subPath;

    @Override
    public ImageRepository<EventImage> getImageRepository() {
        return eventImageRepository;
    }

    @Override
    protected ImageDomain getImageDomain() {
        return ImageDomain.EVENT;
    }

    @Override
    protected String getSubPath() {
        return subPath;
    }

    @Override
    protected EventImage toEntity(ImageRequest imageRequest) {
        return EventImage.builder().url(imageRequest.getUrl()).size(imageRequest.getSize()).build();
    }

    @Transactional
    public void setEvent(EventImage image, Event event) {
        Optional.ofNullable(image).orElseThrow(() -> new EntityParamIsNullException(getImageDomain().name() + " image"));
        Optional.ofNullable(event).orElseThrow(() -> new EntityParamIsNullException("event"));
        image.setEvent(event);
    }
}