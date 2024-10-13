package org.ktc2.cokaen.wouldyouin.like.persist;

import java.util.List;
import java.util.Optional;
import org.ktc2.cokaen.wouldyouin.member.persist.LikeableMember;
import org.ktc2.cokaen.wouldyouin.member.persist.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface LikeRepository <LikeType extends Like<? extends LikeableMember>> extends JpaRepository<LikeType, Long> {

    Optional<LikeType> findByMemberAndLikeableMember(Member member, LikeableMember likeableMember);
    List<LikeType> findAllByMember(Member member);
}

