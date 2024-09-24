package org.ktc2.cokaen.wouldyouin.domain;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Embeddable
public class Location {
    Double longitude;
    Double latitude;
}