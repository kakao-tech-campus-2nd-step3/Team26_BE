package org.ktc2.cokaen.wouldyouin._common.api;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class SliceInfo {

    private final Integer sliceSize;
    private final Long lastId;
}