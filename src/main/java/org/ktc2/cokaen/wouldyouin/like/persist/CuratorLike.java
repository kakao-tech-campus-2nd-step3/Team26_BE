package org.ktc2.cokaen.wouldyouin.like.persist;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class CuratorLike {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private Long curatorId;

    @NotNull
    private Long memberId;

    @Builder

    public CuratorLike(Long curatorId, Long memberId) {
        this.curatorId = curatorId;
        this.memberId = memberId;
    }
}
