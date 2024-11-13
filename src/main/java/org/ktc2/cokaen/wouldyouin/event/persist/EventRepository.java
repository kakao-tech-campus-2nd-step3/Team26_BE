package org.ktc2.cokaen.wouldyouin.event.persist;

import org.ktc2.cokaen.wouldyouin._common.vo.Area;
import org.ktc2.cokaen.wouldyouin._common.vo.Category;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {

    @Query("SELECT E FROM Event E JOIN FETCH E.host "
        + "WHERE E.host.Id = :hostId "
        + "AND E.id > :lastId "
        + "ORDER BY E.id DESC")
    Slice<Event> findAllByHostIdOrderByEventIdDesc(Long hostId, Long lastId, Pageable pageable);

    @Query("SELECT E FROM Event E JOIN FETCH E.host "
        + "WHERE ((:currentLatitude IS NULL Or :currentLongitude IS NULL) OR "
        + "((E.location.latitude between :startLatitude AND :endLatitude) "
        + "AND (E.location.longitude between :startLongitude AND :endLongitude))) "
        + "AND (E.title LIKE %:title%) "
        + "AND (:category = '전체' OR E.category = :category) "
        + "AND (:area = '전체' OR E.area = :area) "
        + "ORDER BY (:currentLatitude - E.location.latitude) * (:currentLatitude - E.location.latitude) + "
        + "(:currentLongitude - E.location.longitude) * (:currentLongitude - E.location.longitude) ASC")
    Slice<Event> findAllByFilterOrderByDistance(
        Double startLatitude, Double startLongitude, Double endLatitude, Double endLongitude,
        Double currentLatitude, Double currentLongitude, String title, Category category, Area area,
        Pageable pageable);
}