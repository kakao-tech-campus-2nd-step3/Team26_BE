package org.ktc2.cokaen.wouldyouin.Image.persist;

import jakarta.persistence.Entity;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
public class EventImage extends Image {

    @Builder
    public EventImage(String name, Long size, String extension) {
        super(name, size, extension);
    }
}