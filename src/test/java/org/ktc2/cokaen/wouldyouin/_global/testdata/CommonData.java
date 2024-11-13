package org.ktc2.cokaen.wouldyouin._global.testdata;

import org.ktc2.cokaen.wouldyouin._common.api.SliceInfo;

public class CommonData {

    public static class path {

        public static final String domainUrl = "https://wouldyouin.store";
    }

    public static class sliceInfo {

        public static SliceInfo get() {
            return SliceInfo.builder()
                .sliceSize(10)
                .lastId(100L)
                .build();
        }
    }

}