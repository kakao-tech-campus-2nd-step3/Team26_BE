package org.ktc2.cokaen.wouldyouin.curation.persist;

import java.util.List;
import org.ktc2.cokaen.wouldyouin._common.persist.Area;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CurationRepository extends JpaRepository<Curation, Long> {

    public List<Curation> findByArea(Area area);
}
