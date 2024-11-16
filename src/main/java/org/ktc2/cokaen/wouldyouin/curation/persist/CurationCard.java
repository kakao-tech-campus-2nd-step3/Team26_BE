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
import java.util.Optional;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.ktc2.cokaen.wouldyouin.image.persist.CurationCardImage;

@Getter
@Setter
@Entity
@EqualsAndHashCode
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CurationCard {

    @Id
    @Column(name = "curation_card_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Long id;

    @Column(name = "subtitle")
    private String subtitle;

    @Column(name = "content")
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "curation_id")
    private Curation curation;

    @OneToMany(mappedBy = "curationCard", fetch = FetchType.LAZY)
    private List<CurationCardImage> curationCardImages = new ArrayList<>();

    @Builder
    public CurationCard(String subtitle, String content, Curation curation,
        List<CurationCardImage> images) {
        Optional.ofNullable(subtitle).ifPresent(this::setSubtitle);
        Optional.ofNullable(content).ifPresent(this::setContent);
        Optional.ofNullable(curation).ifPresent(this::setCuration);
        Optional.ofNullable(images).ifPresent(this::setCurationCardImages);
    }
}