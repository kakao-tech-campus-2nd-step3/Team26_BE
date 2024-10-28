package org.ktc2.cokaen.wouldyouin.Image.persist;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.ktc2.cokaen.wouldyouin.curation.persist.Curation;
import org.ktc2.cokaen.wouldyouin.event.persist.Event;

@Entity
@NoArgsConstructor
public class EventImage extends Image {

    @ManyToOne
    @JoinColumn(name = "event_id")
    private Event event;

    @Builder
    public EventImage(String name, Long size, String extension) {
        super(name, size, extension);
    }
}