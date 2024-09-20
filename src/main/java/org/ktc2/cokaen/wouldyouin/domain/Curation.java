package org.ktc2.cokaen.wouldyouin.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.ktc2.cokaen.wouldyouin.controller.curation.CurationRequest;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Curation {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotNull @Column(nullable = false)
    private UUID curatorId;

    @NotNull
    private String title;

    @NotNull
    private String content;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Area area;

    @Column(nullable = false)
    private LocalDateTime createdTime;

    private String hashTag;

    private UUID eventId;

    @Builder
    protected Curation(UUID curatorId, String title, String content, Area area, String hashTag, UUID eventId) {
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
        return Curation.builder()
            .area(curationRequest.getArea())
            .title(curationRequest.getTitle())
            .curatorId(curationRequest.getCuratorId())
            .eventId(curationRequest.getEventId())
            .content(curationRequest.getContent())
            .hashTag(curationRequest.getHashTag())
            .build();
    }
}
