package org.ktc2.cokaen.wouldyouin.member.persist;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.OneToOne;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.ktc2.cokaen.wouldyouin.image.persist.MemberImage;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn
@EqualsAndHashCode
@ToString
@Entity
public abstract class BaseMember {

    @Id
    @Setter(AccessLevel.NONE)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private AccountType accountType;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private MemberType memberType;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false, unique = true)
    private String nickname;

    @Column(nullable = false)
    private String phone;

    @OneToOne(mappedBy = "baseMember")
    private MemberImage profileImage;

    private String profileImageThumbnailUrl;

    protected BaseMember(AccountType accountType, MemberType memberType, String email, String nickname, String phone,
        MemberImage profileImage, String profileImageThumbnailUrl) {
        this.accountType = accountType;
        this.memberType = memberType;
        this.email = email;
        this.nickname = nickname;
        this.phone = phone;
        this.profileImage = profileImage;
        this.profileImageThumbnailUrl = profileImageThumbnailUrl;
    }

    public String getProfileImageUrl() {
        return profileImage.getName();
    }
}
