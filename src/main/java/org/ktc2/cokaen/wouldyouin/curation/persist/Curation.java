package org.ktc2.cokaen.wouldyouin.curation.persist;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.ktc2.cokaen.wouldyouin.Image.persist.CurationImage;
import org.ktc2.cokaen.wouldyouin._common.persist.Area;
import org.ktc2.cokaen.wouldyouin.curation.api.dto.CurationEditRequest;
import org.ktc2.cokaen.wouldyouin.event.persist.Event;
import org.ktc2.cokaen.wouldyouin.member.persist.Curator;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Curation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "curation_id")
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "curator_id")
    private Curator curator;

    @NotNull
    @Column(name = "title")
    private String title;

    @NotNull
    @Column(name = "content")
    private String content;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "area")
    private Area area;

    @NotNull
    @Column(name = "created_time")
    private LocalDateTime createdTime;

    @NotNull
    @Column(name = "hash_tag")
    private String hashTag;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "event_id")
    private Event event;

    @OneToMany(mappedBy = "curation")
    private List<CurationImage> images;

    @Builder
    public Curation(Curator curator, String title, String content, Area area,
        LocalDateTime createdTime,
        String hashTag, Event event, List<CurationImage> images) {
        this.curator = curator;
        this.title = title;
        this.content = content;
        this.area = area;
        this.createdTime = createdTime;
        this.hashTag = hashTag;
        this.event = event;
        this.images = images;
    }

    public void updateFrom(CurationEditRequest curationEditRequest) {
        Optional.ofNullable(curationEditRequest.getTitle()).ifPresent(this::setTitle);
        Optional.ofNullable(curationEditRequest.getContent()).ifPresent(this::setContent);
        Optional.ofNullable(curationEditRequest.getArea()).ifPresent(this::setArea);
        Optional.ofNullable(curationEditRequest.getCreatedTime()).ifPresent(this::setCreatedTime);
        Optional.ofNullable(curationEditRequest.getHashTag()).ifPresent(this::setHashTag);
//        Optional.ofNullable(curationEditRequest.getImageUrls()).ifPresent(this::setImages);

    }

}
