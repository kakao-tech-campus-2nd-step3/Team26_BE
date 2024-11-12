package org.ktc2.cokaen.wouldyouin.Image.persist;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.ktc2.cokaen.wouldyouin.advertisement.persist.Advertisement;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class AdvertisementImage extends Image {

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "advertisement_id")
    private Advertisement advertisement;

    @Builder
    public AdvertisementImage(String name, Long size, String extension, Advertisement advertisement) {
        super(name, size, extension);
        this.advertisement = advertisement;
    }
}