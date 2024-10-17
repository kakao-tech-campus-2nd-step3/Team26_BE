package org.ktc2.cokaen.wouldyouin.Image.persist;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.ktc2.cokaen.wouldyouin.curation.persist.Curation;

@Entity
@NoArgsConstructor
public class CurationImage extends Image {

    @ManyToOne
    @JoinColumn(name = "curation_id")
    private Curation curation;

    @Builder
    public CurationImage(String name, Long size, String extension) {
        super(name, size, extension);
    }
}