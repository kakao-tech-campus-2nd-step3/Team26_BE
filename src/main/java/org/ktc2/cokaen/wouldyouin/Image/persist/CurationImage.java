package org.ktc2.cokaen.wouldyouin.Image.persist;

import jakarta.persistence.Entity;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
public class CurationImage extends Image {

    @Builder
    public CurationImage(String name, Long size, String extension) {
        super(name, size, extension);
    }
}