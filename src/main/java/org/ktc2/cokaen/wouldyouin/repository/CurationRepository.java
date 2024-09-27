package org.ktc2.cokaen.wouldyouin.repository;

import java.util.List;
import org.ktc2.cokaen.wouldyouin.domain.Area;
import org.ktc2.cokaen.wouldyouin.domain.Curation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CurationRepository extends JpaRepository<Curation, Long> {

    public List<Curation> findByArea(Area area);
}
