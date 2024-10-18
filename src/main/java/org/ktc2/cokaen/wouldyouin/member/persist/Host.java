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
import org.ktc2.cokaen.wouldyouin.event.persist.Event;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DiscriminatorValue("Host")
@Entity
public class Host extends BaseMember implements LikeableMember {

    @Column(nullable = false)
    private String hashedPassword;

    @Column(nullable = false)
    private String intro;

    @Column(nullable = false)
    private Integer likes;

    @Column(nullable = false)
    private String hashtag;

    @OneToMany(mappedBy = "host")
    private List<Event> events;

    @Builder
    protected Host(String email, String nickname, String phone, String hashedPassword) {
        super(AccountType.local, MemberType.host, email, nickname, phone, List.of());
        this.hashedPassword = hashedPassword;
        this.intro = "";
        this.likes = 0;
        this.hashtag = "";
    }
}
