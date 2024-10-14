package org.ktc2.cokaen.wouldyouin.Image.persist;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class Image {

    @Id
    @GeneratedValue
    @Column(name = "image_id")
    protected Long id;

    @NotNull
    @Column(name = "url")
    protected String url;

    @Column(name = "size")
    private Long size;

    @CreatedDate
    @Column(name = "created_date")
    private LocalDateTime createdDate;

    protected Image(String url, Long size, String extension) {
        this.url = url;
        this.size = size;
    }
}