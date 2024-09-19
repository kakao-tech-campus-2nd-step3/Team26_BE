package org.ktc2.cokaen.wouldyouin.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import java.util.UUID;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
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

    protected Member() {

    }
}
