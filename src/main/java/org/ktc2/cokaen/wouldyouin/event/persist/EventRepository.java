package org.ktc2.cokaen.wouldyouin.event.persist;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {

    public List<Event> findByHostId(Long hostId);
}