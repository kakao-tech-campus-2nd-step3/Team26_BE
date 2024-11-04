package org.ktc2.cokaen.wouldyouin.event.persist;

import org.ktc2.cokaen.wouldyouin._common.persist.Area;
import org.ktc2.cokaen.wouldyouin._common.persist.Category;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {

    @Query("SELECT E FROM Event E JOIN FETCH E.host JOIN FETCH E.images "
        + "WHERE E.host.Id = :hostId "
        + "AND E.host.Id > :lastId "
        + "ORDER BY E.createdDate DESC")
    Slice<Event> findAllByHostIdOrderByCreatedDateDesc(Long hostId, Long lastId, Pageable pageable);

    @Query("SELECT E FROM Event E JOIN FETCH E.host JOIN FETCH E.images "
        + "WHERE ((:lat1 IS NULL OR :long1 IS NULL OR :lat2 IS NULL OR :long2 IS NULL) "
        + "OR ((E.location.latitude between :lat1 AND :lat2) AND (E.location.longitude between :long1 AND :long2))) "
        + "AND (:category IS NULL OR E.category = :category) "
        + "AND (:area IS NULL OR E.area = :area) "
        + "ORDER BY (:lat - E.location.latitude) * (:lat - E.location.latitude) + "
        + "(:long - E.location.longitude) * (:long - E.location.longitude) ASC")
    Slice<Event> findAllByFilterOrderByDistance(
        @Param("lat1") Double startLatitude, @Param("long1") Double startLongitude,
        @Param("lat2") Double endLatitude, @Param("long2") Double endLongitude,
        @Param("lat") Double currentLatitude, @Param("long") Double currentLongitude,
        Category category, Area area, Pageable pageable);
}
