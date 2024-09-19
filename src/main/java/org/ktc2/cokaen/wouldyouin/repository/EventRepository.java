package org.ktc2.cokaen.wouldyouin.repository;

import java.util.List;
import java.util.UUID;
import org.ktc2.cokaen.wouldyouin.domain.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventRepository extends JpaRepository<Event, UUID> {

    public List<Event> findByHostId(UUID hostId);
}