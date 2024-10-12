package org.ktc2.cokaen.wouldyouin.member.persist;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import java.util.Arrays;
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
public class Host extends BaseMember {

    @Column(nullable = false)
    private String hashedPassword;

    @Column(nullable = false)
    private String intro;

    @Column(nullable = false)
    private Integer followers;

    @Column(nullable = false)
    private String hashtag;

    @OneToMany
    private List<Event> events;

    @Builder
    protected Host(String email, String nickname, String phone, String hashedPassword) {
        super(AccountType.local, MemberType.host, email, nickname, phone, "");
        this.hashedPassword = hashedPassword;
        this.intro = "";
        this.followers = 0;
        this.hashtag = "";
    }

    public List<String> getHashTagList() {
        //TODO: 정상적으로 해시태그 리스트로 분리되는지 검증 필요
        return Arrays.stream(hashtag.split("#")).toList();
    }
}
