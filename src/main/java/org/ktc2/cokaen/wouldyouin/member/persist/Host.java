package org.ktc2.cokaen.wouldyouin.member.persist;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Helper;
import lombok.experimental.SuperBuilder;
import org.ktc2.cokaen.wouldyouin.Image.persist.MemberImage;
import org.ktc2.cokaen.wouldyouin._common.converter.HashtagConverter;
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
    @Convert(converter = HashtagConverter.class)
    private List<String> hashtags;

    @OneToMany(mappedBy = "host", fetch = FetchType.LAZY)
    private List<Event> events = new ArrayList<>();

    @Builder
    protected Host(String email, String nickname, String phone, String hashedPassword, MemberImage profileImage) {
        super(AccountType.local, MemberType.host, email, nickname, phone, profileImage);
        this.hashedPassword = hashedPassword;
        this.intro = "";
        this.likes = 0;
        this.hashtags = new ArrayList<>();
    }
}
