package org.ktc2.cokaen.wouldyouin.curation.persist;

import org.ktc2.cokaen.wouldyouin._common.vo.Area;
import org.ktc2.cokaen.wouldyouin.member.persist.Curator;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CurationRepository extends JpaRepository<Curation, Long> {

    @Query("SELECT C FROM Curation C JOIN FETCH C.curator "
        + "WHERE C.area = :area AND C.id > :lastId "
        + "ORDER BY C.id DESC")
    Slice<Curation> findAllByAreaOrderByCreatedDateDesc(Area area, Long lastId, Pageable pageable);

    @Query("SELECT C FROM Curation C JOIN FETCH C.curator "
        + "WHERE C.curator.id = :curatorId AND C.id > :lastId "
        + "ORDER BY C.id DESC")
    Slice<Curation> findAllByCuratorOrderByCreatedDateDesc(Long curatorId, Long lastId,
        Pageable pageable);
}