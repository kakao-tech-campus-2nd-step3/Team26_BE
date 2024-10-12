package org.ktc2.cokaen.wouldyouin.member.persist;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.ktc2.cokaen.wouldyouin._common.persist.Area;
import org.ktc2.cokaen.wouldyouin.curation.persist.Curation;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DiscriminatorValue("Curator")
@Entity
public class Curator extends Member implements LikeableMember {

    @Column(nullable = false)
    private String intro;

    @Column(nullable = false)
    private Integer followers;

    @OneToMany(mappedBy = "curator")
    private List<Curation> curations;

    @Builder(builderMethodName = "curatorBuilder")
    public Curator(AccountType accountType, String email, String nickname, String phone, String profileImageUrl, Area area, String gender, String socialId) {
        super(accountType, MemberType.curator, email, nickname, phone, profileImageUrl, area, gender, socialId);
        this.intro = "";
        this.followers = 0;
    }
}
