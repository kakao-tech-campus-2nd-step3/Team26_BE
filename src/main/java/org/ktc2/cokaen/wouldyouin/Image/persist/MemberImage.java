package org.ktc2.cokaen.wouldyouin.Image.persist;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.ktc2.cokaen.wouldyouin.curation.persist.Curation;
import org.ktc2.cokaen.wouldyouin.member.persist.BaseMember;

@Entity
@NoArgsConstructor
public class MemberImage extends Image {

    @ManyToOne
    @JoinColumn(name = "base_member_id")
    private BaseMember baseMember;

    @Builder
    public MemberImage(String name, Long size, String extension) {
        super(name, size, extension);
    }
}