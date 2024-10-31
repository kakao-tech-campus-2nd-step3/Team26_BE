package org.ktc2.cokaen.wouldyouin.Image.persist;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventImageRepository extends ImageRepository<EventImage> {

}