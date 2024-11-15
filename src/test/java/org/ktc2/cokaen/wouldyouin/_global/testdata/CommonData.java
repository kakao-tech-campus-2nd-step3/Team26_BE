package org.ktc2.cokaen.wouldyouin._global.testdata;

import org.ktc2.cokaen.wouldyouin._common.api.SliceInfo;
import org.ktc2.cokaen.wouldyouin._global.testdata.CurationData.R.curation1;
import org.ktc2.cokaen.wouldyouin._global.testdata.EventData.R.event1;
import org.ktc2.cokaen.wouldyouin._global.testdata.MemberData.R;
import org.ktc2.cokaen.wouldyouin._global.testdata.MemberData.R.curator1;
import org.ktc2.cokaen.wouldyouin._global.testdata.MemberData.R.host1;
import org.ktc2.cokaen.wouldyouin._global.testdata.ReservationData.R.reservation1;
import org.ktc2.cokaen.wouldyouin._global.testdata.ReviewData.R.review1;

public class CommonData {

    public static class path {

        public static final String domainUrl = "https://wouldyouin.store";
    }

    public static class sliceInfo {
        public static class event{
            public static SliceInfo get(){
                return SliceInfo.builder()
                    .sliceSize(10)
                    .lastId(event1.id)
                    .build();
            }
        }

        public static class curation {

            public static SliceInfo get() {
                return SliceInfo.builder()
                    .sliceSize(10)
                    .lastId(curation1.id)
                    .build();
            }
        }

        public static class like {
            public static class normal1 {
                public static class hostLikes {
                    public static SliceInfo get() {
                        return SliceInfo.builder()
                            .sliceSize(1)
                            .lastId(host1.id)
                            .build();
                    }
                }
                public static class curatorLikes {
                    public static SliceInfo get() {
                        return SliceInfo.builder()
                            .sliceSize(1)
                            .lastId(R.curator1.id)
                            .build();
                    }
                }
            }
            public static class curator1 {
                public static class hostLikes {
                    public static SliceInfo get() {
                        return SliceInfo.builder()
                            .sliceSize(1)
                            .lastId(host1.id)
                            .build();
                    }
                }
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
                    .sliceSize(20)
                    .lastId(review1.id)
                    .build();
            }
        }
    }
}