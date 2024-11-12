package org.ktc2.cokaen.wouldyouin.curation.persist;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CurationCardRepository extends JpaRepository<CurationCard, Long> {

    @Query("SELECT CC FROM CurationCard CC JOIN FETCH CC.curation WHERE CC.curation = :curation ORDER BY CC.id ASC")
    List<CurationCard> findAllByCurationOrderByIdAsc(Curation curation);
}