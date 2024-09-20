package org.ktc2.cokaen.wouldyouin.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Member {

    @Column
    @Id
    private UUID id;
    @Column(nullable = false)
    private String nickname;
    @Column(nullable = false)
    private Area area;
    @Column(nullable = false)
    private String userType;
    @Column(nullable = false)
    private String gender;
    @Column
    private String phone;
    @Column
    private String profileImageUrl;

    @Builder()
    protected Member(String nickname, Area area, String userType, String gender, String phone, String profileImageUrl) {
        this.nickname = nickname;
        this.area = area;
        this.userType = userType;
        this.gender = gender;
        this.phone = phone;
        this.profileImageUrl = profileImageUrl;
    }
}
