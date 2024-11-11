package org.ktc2.cokaen.wouldyouin.Image.persist;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    @Column(name = "image_id")
    protected Long id;

    @NotNull
    @Column(name = "name")
    protected String name;

    @Column(name = "size")
    private Long size;

    @Column(name = "extension")
    private String extension;

    @CreatedDate
    @Column(name = "created_date")
    private LocalDateTime createdDate;

    protected Image(String name, Long size, String extension) {
        this.name = name;
        this.size = size;
        this.extension = extension;
    }
}