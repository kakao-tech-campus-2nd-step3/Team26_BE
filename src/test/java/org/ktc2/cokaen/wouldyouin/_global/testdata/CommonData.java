package org.ktc2.cokaen.wouldyouin._global.testdata;

import org.ktc2.cokaen.wouldyouin._common.api.SliceInfo;
import org.ktc2.cokaen.wouldyouin._global.testdata.ReservationData.R.reservation1;
import org.ktc2.cokaen.wouldyouin._global.testdata.ReviewData.R.review1;

public class CommonData {

    public static class path {

        public static final String domainUrl = "https://wouldyouin.store";
    }

    public static class sliceInfo {

        public static class curation {

            public static SliceInfo get() {
                return SliceInfo.builder()
                    .sliceSize(10)
                    .lastId(301L)
                    .build();
            }
        }

        public static class reservation {

            public static SliceInfo get() {
                return SliceInfo.builder()
                    .sliceSize(10)
                    .lastId(reservation1.id)
                    .build();
            }
        }

        public static class review {

            public static SliceInfo get() {
                return SliceInfo.builder()
                    .sliceSize(10)
                    .lastId(review1.id)
                    .build();
            }
        }
    }
}