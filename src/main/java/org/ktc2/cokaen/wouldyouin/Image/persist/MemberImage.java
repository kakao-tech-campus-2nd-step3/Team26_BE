package org.ktc2.cokaen.wouldyouin.Image.persist;

import jakarta.persistence.Entity;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
public class MemberImage extends Image {

    @Builder
    public MemberImage(String name, Long size, String extension) {
        super(name, size, extension);
    }
}