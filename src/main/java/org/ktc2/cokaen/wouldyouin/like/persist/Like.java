package org.ktc2.cokaen.wouldyouin.like.persist;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MappedSuperclass;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.ktc2.cokaen.wouldyouin.member.persist.LikeableMember;
import org.ktc2.cokaen.wouldyouin.member.persist.Member;
import org.ktc2.cokaen.wouldyouin.member.persist.MemberType;

@Getter
@Setter
@MappedSuperclass
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class Like<T extends LikeableMember> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @ManyToOne
    @JoinColumn
    private T likeableMember;

    @NotNull
    @Column
    private MemberType likeableMemberType;

    @NotNull
    @ManyToOne
    @JoinColumn
    private Member member;

    protected Like(T likeableMember, MemberType likeableMemberType, Member member) {
        this.likeableMember = likeableMember;
        this.likeableMemberType = likeableMemberType;
        this.member = member;
    }
}

