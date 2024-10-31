package org.ktc2.cokaen.wouldyouin.Image.persist;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.ktc2.cokaen.wouldyouin.curation.persist.Curation;
import org.ktc2.cokaen.wouldyouin.curation.persist.CurationCard;

@Entity
@Setter
@NoArgsConstructor
public class CurationImage extends Image {

    @ManyToOne
    @JoinColumn(name = "curation_card_id")
    private CurationCard curationCard;

    @Builder
    public CurationImage(String name, Long size, String extension) {
        super(name, size, extension);
    }
}