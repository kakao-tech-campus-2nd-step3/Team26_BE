package org.ktc2.cokaen.wouldyouin.Image.persist;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.ktc2.cokaen.wouldyouin.event.persist.Event;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class EventImage extends Image {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_id")
    private Event event;

    @Builder
    public EventImage(String name, Long size, String extension) {
        super(name, size, extension);
    }
}