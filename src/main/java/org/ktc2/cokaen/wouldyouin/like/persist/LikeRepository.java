package org.ktc2.cokaen.wouldyouin.like.persist;


import java.util.Optional;
import org.ktc2.cokaen.wouldyouin.member.persist.LikeableMember;
import org.ktc2.cokaen.wouldyouin.member.persist.Member;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface LikeRepository <LikeType extends Like<? extends LikeableMember>> extends JpaRepository<LikeType, Long> {
    Optional<LikeType> findByMemberAndLikeableMember(Member member, LikeableMember likeableMember);

    @Query("SELECT l FROM #{#entityName} l " +
        "JOIN FETCH l.member m " +
        "JOIN FETCH l.likeableMember lm " +
        "WHERE l.member = :member " +
        "AND l.id < :lastId " +
        "ORDER BY l.id DESC")
    Slice<LikeType> findAllByMember(Member member, Long lastId, Pageable pageable);
}
