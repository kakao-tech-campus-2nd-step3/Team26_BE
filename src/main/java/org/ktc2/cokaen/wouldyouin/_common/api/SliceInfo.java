package org.ktc2.cokaen.wouldyouin._common.api;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@Builder
@EqualsAndHashCode
@ToString
public class SliceInfo {

    private final Integer sliceSize;
    private final Long lastId;
}