package org.ktc2.cokaen.wouldyouin.member.persist;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.ktc2.cokaen.wouldyouin._common.converter.HashtagConverter;
import org.ktc2.cokaen.wouldyouin._common.vo.Area;
import org.ktc2.cokaen.wouldyouin.curation.persist.Curation;
import org.ktc2.cokaen.wouldyouin.image.persist.MemberImage;
import org.ktc2.cokaen.wouldyouin.member.api.dto.request.edit.CuratorEditRequest;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DiscriminatorValue("Curator")
@EqualsAndHashCode(callSuper = true)
@ToString
@Entity
public class Curator extends Member implements LikeableMember {

    @Column(nullable = false)
    private String intro;

    @Column(nullable = false)
    private Integer likes;

    @Column(nullable = false)
    @Convert(converter = HashtagConverter.class)
    private List<String> hashtags;

    @OneToMany(mappedBy = "curator", fetch = FetchType.LAZY)
    private List<Curation> curations = new ArrayList<>();

    @Builder(builderMethodName = "curatorBuilder")
    public Curator(AccountType accountType, String email, String nickname, String phone, MemberImage profileImage, String profileImageThumbnailUrl, Area area, String gender,
        String socialId) {
        super(accountType, MemberType.curator, email, nickname, phone, profileImage, profileImageThumbnailUrl, area, gender, socialId);
        this.intro = "";
        this.likes = 0;
        hashtags = new ArrayList<>();
    }

    public void updateFrom(CuratorEditRequest request, MemberImage image, String profileImageThumbnailUrl) {
        Optional.ofNullable(request.getPhoneNumber()).ifPresent(this::setPhone);
        Optional.ofNullable(request.getNickname()).ifPresent(this::setNickname);
        Optional.ofNullable(request.getArea()).ifPresent(this::setArea);
        Optional.ofNullable(request.getIntro()).ifPresent(this::setIntro);
        Optional.ofNullable(image).ifPresent(this::setProfileImage);
        Optional.ofNullable(profileImageThumbnailUrl).ifPresent(this::setProfileImageThumbnailUrl);
    }
}
