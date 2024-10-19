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
import jakarta.persistence.OneToMany;
import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.ktc2.cokaen.wouldyouin.Image.persist.MemberImage;

@Slf4j
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn
@Entity
public abstract class BaseMember {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

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

    @Column(nullable = false)
    @OneToMany(mappedBy = "baseMember")
    private List<MemberImage> profileImage;

    protected BaseMember(AccountType accountType, MemberType memberType, String email, String nickname, String phone, List<MemberImage> profileImage) {
        this.accountType = accountType;
        this.memberType = memberType;
        this.email = email;
        this.nickname = nickname;
        this.phone = phone;
        this.profileImage = profileImage;
    }

    @Override
    public String toString() {
        return "BaseMember{" +
            "Id=" + Id +
            ", accountType=" + accountType +
            ", memberType=" + memberType +
            ", email='" + email + '\'' +
            ", nickname='" + nickname + '\'' +
            ", phone='" + phone + '\'' +
            ", profileImage=" + profileImage +
            '}';
    }

    public String getProfileImageUrl() {
        // TODO: 멤버 조회시 profileImage에 null 들어가는 원인 파악 필요
        log.warn("#### in baseMember.getProfileImageUrl() for baseMember: {}", this);
        return profileImage.getFirst().getUrl();
    }
}
