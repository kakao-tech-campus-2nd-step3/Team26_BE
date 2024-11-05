package org.ktc2.cokaen.wouldyouin.curation.persist;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.ktc2.cokaen.wouldyouin.Image.persist.CurationImage;

@Getter
@Setter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CurationCard {

    @Id
    @Column(name = "curation_card_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "subtitle")
    private String subtitle;

    @Column(name = "content")
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "curation_id")
    private Curation curation;

    @OneToMany(mappedBy = "curationCard", fetch = FetchType.LAZY)
    private List<CurationImage> curationImages = new ArrayList<>();

    @Builder
    public CurationCard(String subtitle, String content, Curation curation, List<CurationImage> images) {
        this.subtitle = subtitle;
        this.content = content;
        this.curation = curation;
        this.curationImages = images;
    }
}