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
import org.ktc2.cokaen.wouldyouin.like.persist.CuratorLike;
import org.ktc2.cokaen.wouldyouin.like.persist.HostLike;
import org.ktc2.cokaen.wouldyouin.reservation.persist.Reservation;
import org.ktc2.cokaen.wouldyouin.review.persist.Review;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DiscriminatorValue("Member")
@Entity
public class Member extends BaseMember {

    @Column(nullable = false)
    private Area area;

    @Column(nullable = false)
    private String gender;

    @Column
    private String socialId; //소셜 타입 식별자 값

    //for JWT refresh token
    @Column(length = 1000)
    private String refreshToken;

    @OneToMany
    private List<CuratorLike> curatorLikes;

    @OneToMany
    private List<HostLike> hostLikes;

    @OneToMany
    private List<Reservation> reservations;

    @OneToMany
    private List<Review> reviews;

    // for Curator
    protected Member(AccountType accountType, MemberType memberType, String email, String nickname, String phone, String profileImageUrl, Area area, String gender, String socialId) {
        super(accountType, memberType, email, nickname, phone, profileImageUrl);
        this.area = area;
        this.gender = gender;
        this.socialId = socialId;
    }

    @Builder
    // for public builder
    protected Member(AccountType accountType, String email, String nickname, String phone, String profileImageUrl, Area area, String gender, String socialId) {
        this(accountType, MemberType.normal, email, nickname, phone, profileImageUrl, area, gender, socialId);
    }
}
