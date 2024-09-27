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
@DiscriminatorValue("Host")
@Entity
public class Host extends AbstractMember {

    @Column(nullable = false)
    private String intro;

    @Column(nullable = false)
    private Integer followers;

    @Column(nullable = false)
    private String hashtag;

    @Builder
    protected Host(String nickname, String phone, String profileUrl, String intro, Integer followers, String hashtag) {
        super(nickname, phone, profileUrl);
        this.intro = intro;
        this.followers = followers;
        this.hashtag = hashtag;
    }
}
