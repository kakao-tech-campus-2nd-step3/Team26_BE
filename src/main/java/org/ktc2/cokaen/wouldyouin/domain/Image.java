package org.ktc2.cokaen.wouldyouin.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;

@Entity
public class Image {
    @Id
    @GeneratedValue
    private Long id;

    @NotNull
    private String url;
}