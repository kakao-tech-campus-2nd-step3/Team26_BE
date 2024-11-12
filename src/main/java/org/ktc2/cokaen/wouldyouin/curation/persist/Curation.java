package org.ktc2.cokaen.wouldyouin.curation.persist;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.ktc2.cokaen.wouldyouin._common.converter.HashtagConverter;
import org.ktc2.cokaen.wouldyouin._common.vo.Area;
import org.ktc2.cokaen.wouldyouin.curation.api.dto.CurationEditRequest;
import org.ktc2.cokaen.wouldyouin.event.persist.Event;
import org.ktc2.cokaen.wouldyouin.member.persist.Curator;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Curation {

    @Id
    @Column(name = "curation_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Long id;

    @JoinColumn(name = "curator_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Curator curator;

    @NotNull
    @Column(name = "title")
    private String title;

    @NotNull
    @Column(name = "content")
    private String content;

    @OneToMany(mappedBy = "curation", fetch = FetchType.LAZY)
    private List<CurationCard> curationCards;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "area")
    private Area area;

    @Column(name = "hashtag")
    @Convert(converter = HashtagConverter.class)
    private List<String> hashtags;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "curation_event_relation",
        joinColumns = @JoinColumn(name = "curation_id"),
        inverseJoinColumns = @JoinColumn(name = "event_id")
    )
    private List<Event> events;

    @CreatedDate
    @Column(name = "created_date")
    private LocalDateTime createdDate;

    @LastModifiedDate
    @Column(name = "modified_date")
    private LocalDateTime modifiedDate;

    @Builder
    public Curation(Curator curator, String title, String content, List<CurationCard> curationCards, Area area, List<String> hashtags,
        List<Event> events) {
        this.curator = curator;
        this.title = title;
        this.content = content;
        Optional.ofNullable(curationCards).ifPresent(this::setCurationCards);
        this.area = area;
        Optional.ofNullable(hashtags).ifPresent(this::setHashtags);
        Optional.ofNullable(events).ifPresent(this::setEvents);
    }

    public void updateFrom(CurationEditRequest curationEditRequest, List<CurationCard> curationCards, List<Event> events) {
        Optional.ofNullable(curationEditRequest.getTitle()).ifPresent(this::setTitle);
        Optional.ofNullable(curationEditRequest.getContent()).ifPresent(this::setContent);
        Optional.ofNullable(curationEditRequest.getArea()).ifPresent(this::setArea);
        Optional.ofNullable(curationEditRequest.getHashtags()).ifPresent(this::setHashtags);
        Optional.ofNullable(curationCards).ifPresent(this::setCurationCards);
        Optional.ofNullable(events).ifPresent(this::setEvents);
    }
}