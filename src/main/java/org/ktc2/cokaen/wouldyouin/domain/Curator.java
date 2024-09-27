package org.ktc2.cokaen.wouldyouin.domain;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DiscriminatorValue("Curator")
@Entity
public class Curator extends Member {

    @Column(nullable = false)
    private String intro;

    @Column(nullable = false)
    private Integer followers;

    @Builder
    protected Curator(String nickname, Area area, String gender, String phone, String profileImageUrl, String intro,
        Integer followers) {
        super(nickname, area, gender, phone, profileImageUrl);
        this.intro = intro;
        this.followers = followers;
    }
}
