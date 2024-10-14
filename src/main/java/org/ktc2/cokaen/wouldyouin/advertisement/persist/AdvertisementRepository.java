package org.ktc2.cokaen.wouldyouin.advertisement.persist;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AdvertisementRepository extends JpaRepository<Advertisement, Long> {

    @Query("SELECT a FROM Advertisement a WHERE a.endTime > :currentTime")
    List<Advertisement> findByCurrentTime(LocalDateTime currentTime);
}
