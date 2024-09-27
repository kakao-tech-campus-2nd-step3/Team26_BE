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
@DiscriminatorValue("Member")
@Entity
public class Member extends AbstractMember {

    @Column(nullable = false)
    private Area area;

    @Column(nullable = false)
    private String gender;

    @Builder()
    protected Member(String nickname, Area area, String gender, String phone, String profileImageUrl) {
        super(nickname, phone, profileImageUrl);
        this.area = area;
        this.gender = gender;
    }
}
