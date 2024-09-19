package org.ktc2.cokaen.wouldyouin.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.ktc2.cokaen.wouldyouin.controller.curation.CurationRequest;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Curation {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private UUID curatorId;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Area area;

    @Column(nullable = false)
    private LocalDateTime createdTime;

    private String hashTag;

    private UUID eventId;

    public Curation(UUID eventId, String hashTag, Area area, UUID curatorId, String title,
        String content) {

    }

    public Curation(UUID curatorId, String title, String content, Area area, String hashTag,
        UUID eventId) {
        this.curatorId = curatorId;
        this.title = title;
        this.content = content;
        this.area = area;
        this.createdTime = LocalDateTime.now();
        this.hashTag = hashTag;
        this.eventId = eventId;
    }

    public void setFrom(CurationRequest curationRequest) {
        this.curatorId = curationRequest.getCuratorId();
        this.title = curationRequest.getTitle();
        this.content = curationRequest.getContent();
        this.area = curationRequest.getArea();
        this.hashTag = curationRequest.getHashTag();
        this.eventId = curationRequest.getEventId();
    }

    public static Curation from(CurationRequest curationRequest) {
        return new Curation(curationRequest.getCuratorId(), curationRequest.getTitle(),
            curationRequest.getContent(), curationRequest.getArea(), curationRequest.getHashTag(),
            curationRequest.getEventId());
    }
}
