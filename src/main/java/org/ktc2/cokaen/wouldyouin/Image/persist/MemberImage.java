package org.ktc2.cokaen.wouldyouin.Image.persist;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.ktc2.cokaen.wouldyouin.member.persist.BaseMember;

@Entity
@Setter
@NoArgsConstructor
public class MemberImage extends Image {

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "base_member_id")
    @Setter
    private BaseMember baseMember;

    @Builder
    public MemberImage(String name, Long size, String extension) {
        super(name, size, extension);
    }
}