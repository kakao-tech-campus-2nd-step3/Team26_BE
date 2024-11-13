package org.ktc2.cokaen.wouldyouin._global.testdata;

import java.time.LocalDateTime;
import org.ktc2.cokaen.wouldyouin.image.api.dto.ImageRequest;
import org.ktc2.cokaen.wouldyouin.image.api.dto.ImageResponse;
import org.ktc2.cokaen.wouldyouin.image.persist.AdvertisementImage;
import org.ktc2.cokaen.wouldyouin.image.persist.CurationImage;
import org.ktc2.cokaen.wouldyouin.image.persist.EventImage;
import org.ktc2.cokaen.wouldyouin.image.persist.Image;
import org.ktc2.cokaen.wouldyouin.image.persist.MemberImage;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.util.ReflectionTestUtils;

public class ImageData {

    // name: 파일 이름만 있어야 함 => entity, request
    // 예: memberImage1.png
    // url: 도메인 그거 => response
    // 예: https://localhost:8080/images/member/Image1.png

    public static class childPath {
        public static final String member = "/api/images/member";
        public static final String event = "/api/images/events";
        public static final String curation = "/api/images/curation";
        public static final String advertisement = "/api/images/advertisement";
    }

    public static String getThumbnailUrl(AdvertisementImage image) {
        return CommonData.path.domainUrl + childPath.advertisement + "/thumbnails/" + image.getName();
    }
    public static String getThumbnailUrl(CurationImage image) {
        return CommonData.path.domainUrl + childPath.curation + "/thumbnails/" + image.getName();
    }
    public static String getThumbnailUrl(EventImage image) {
        return CommonData.path.domainUrl + childPath.event + "/thumbnails/" + image.getName();
    }
    public static String getThumbnailUrl(MemberImage image) {
        return CommonData.path.domainUrl + childPath.member + "/thumbnails/" + image.getName();
    }

    public static class R {
        public static class member {
            public static class normal {
                public static final Long id = 1101L;
                public static final String name = "memberImage1.jpg";
                public static final String url = CommonData.path.domainUrl + childPath.member + name;
                public static final Long size = 11000L;
                public static final String extension = ".jpg";
                public static final LocalDateTime createdDate = LocalDateTime.of(2024, 3, 23, 0, 0);
            }
            public static class curator {
                public static final Long id = 1102L;
                public static final String name = "curatorImage1.jpg";
                public static final String url = CommonData.path.domainUrl + childPath.member + name;
                public static final Long size = 12000L;
                public static final String extension = ".jpg";
                public static final LocalDateTime createdDate = LocalDateTime.of(2024, 3, 23, 0, 0);
            }
            public static class host {
                public static final Long id = 1103L;
                public static final String name = "hostImage1.jpg";
                public static final String url = CommonData.path.domainUrl + childPath.member + name;
                public static final Long size = 13000L;
                public static final String extension = ".jpg";
                public static final LocalDateTime createdDate = LocalDateTime.of(2024, 3, 23, 0, 0);
            }
            public static class welcome {
                public static final Long id = 1104L;
                public static final String name = "welcomeImage1.jpg";
                public static final String url = CommonData.path.domainUrl + childPath.member + name;
                public static final Long size = 14000L;
                public static final String extension = ".jpg";
                public static final LocalDateTime createdDate = LocalDateTime.of(2024, 3, 23, 0, 0);
            }
        }

        public static class event1 {
            public static final Long id = 1201L;
            public static final String name = "eventImage1.jpg";
            public static final String url = CommonData.path.domainUrl + childPath.event + name;
            public static final Long size = 21000L;
            public static final String extension = ".jpg";
            public static final LocalDateTime createdDate = LocalDateTime.of(2024, 3, 23, 0, 0);
        }
        public static class event2 {
            public static final Long id = 1202L;
            public static final String name = "eventImage2.jpg";
            public static final String url = CommonData.path.domainUrl + childPath.event + name;
            public static final Long size = 22000L;
            public static final String extension = ".jpg";
            public static final LocalDateTime createdDate = LocalDateTime.of(2024, 3, 23, 0, 0);
        }
        public static class event3 {
            public static final Long id = 1203L;
            public static final String name = "eventImage3.jpg";
            public static final String url = CommonData.path.domainUrl + childPath.event + name;
            public static final Long size = 23000L;
            public static final String extension = ".jpg";
            public static final LocalDateTime createdDate = LocalDateTime.of(2024, 3, 23, 0, 0);
        }
        public static class event4 {
            public static final Long id = 1204L;
            public static final String name = "eventImage4.jpg";
            public static final String url = CommonData.path.domainUrl + childPath.event + name;
            public static final Long size = 24000L;
            public static final String extension = ".jpg";
            public static final LocalDateTime createdDate = LocalDateTime.of(2024, 3, 23, 0, 0);
        }
        public static class event5 {
            public static final Long id = 1205L;
            public static final String name = "eventImage5.jpg";
            public static final String url = CommonData.path.domainUrl + childPath.event + name;
            public static final Long size = 25000L;
            public static final String extension = ".jpg";
            public static final LocalDateTime createdDate = LocalDateTime.of(2024, 3, 23, 0, 0);
        }

        public static class curation1 {
            public static final Long id = 1301L;
            public static final String name = "curationImage1.jpg";
            public static final String url = CommonData.path.domainUrl + childPath.curation + name;
            public static final Long size = 31000L;
            public static final String extension = ".jpg";
            public static final LocalDateTime createdDate = LocalDateTime.of(2024, 3, 23, 0, 0);
        }
        public static class curation2 {
            public static final Long id = 1302L;
            public static final String name = "curationImage2.jpg";
            public static final String url = CommonData.path.domainUrl + childPath.curation + name;
            public static final Long size = 32000L;
            public static final String extension = ".jpg";
            public static final LocalDateTime createdDate = LocalDateTime.of(2024, 3, 23, 0, 0);
        }
        public static class curation3 {
            public static final Long id = 1303L;
            public static final String name = "curationImage3.jpg";
            public static final String url = CommonData.path.domainUrl + childPath.curation + name;
            public static final Long size = 33000L;
            public static final String extension = ".jpg";
            public static final LocalDateTime createdDate = LocalDateTime.of(2024, 3, 23, 0, 0);
        }
        public static class curation4 {
            public static final Long id = 1304L;
            public static final String name = "curationImage4.jpg";
            public static final String url = CommonData.path.domainUrl + childPath.curation + name;
            public static final Long size = 34000L;
            public static final String extension = ".jpg";
            public static final LocalDateTime createdDate = LocalDateTime.of(2024, 3, 23, 0, 0);
        }
        public static class curation5 {
            public static final Long id = 1305L;
            public static final String name = "curationImage5.jpg";
            public static final String url = CommonData.path.domainUrl + childPath.curation + name;
            public static final Long size = 35000L;
            public static final String extension = ".jpg";
            public static final LocalDateTime createdDate = LocalDateTime.of(2024, 3, 23, 0, 0);
        }
        public static class curation6 {
            public static final Long id = 1306L;
            public static final String name = "curationImage6.jpg";
            public static final String url = CommonData.path.domainUrl + childPath.curation + name;
            public static final Long size = 36000L;
            public static final String extension = ".jpg";
            public static final LocalDateTime createdDate = LocalDateTime.of(2024, 3, 23, 0, 0);
        }
        public static class curation7 {
            public static final Long id = 1307L;
            public static final String name = "curationImage7.jpg";
            public static final String url = CommonData.path.domainUrl + childPath.curation + name;
            public static final Long size = 37000L;
            public static final String extension = ".jpg";
            public static final LocalDateTime createdDate = LocalDateTime.of(2024, 3, 23, 0, 0);
        }
        public static class curation8 {
            public static final Long id = 1308L;
            public static final String name = "curationImage8.jpg";
            public static final String url = CommonData.path.domainUrl + childPath.curation + name;
            public static final Long size = 38000L;
            public static final String extension = ".jpg";
            public static final LocalDateTime createdDate = LocalDateTime.of(2024, 3, 23, 0, 0);
        }

        public static class advertisement1 {
            public static final Long id = 1401L;
            public static final String name = "adImage1.jpg";
            public static final String url = CommonData.path.domainUrl + childPath.advertisement + name;
            public static final Long size = 41000L;
            public static final String extension = ".jpg";
            public static final LocalDateTime createdDate = LocalDateTime.of(2024, 3, 23, 0, 0);
        }
        public static class advertisement2 {
            public static final Long id = 1402L;
            public static final String name = "adImage2.jpg";
            public static final String url = CommonData.path.domainUrl + childPath.advertisement + name;
            public static final Long size = 42000L;
            public static final String extension = ".jpg";
            public static final LocalDateTime createdDate = LocalDateTime.of(2024, 3, 23, 0, 0);
        }
        public static class advertisement3 {
            public static final Long id = 1403L;
            public static final String name = "adImage3.jpg";
            public static final String url = CommonData.path.domainUrl + childPath.advertisement + name;
            public static final Long size = 43000L;
            public static final String extension = ".jpg";
            public static final LocalDateTime createdDate = LocalDateTime.of(2024, 3, 23, 0, 0);
        }

        public static class mockMultipartFile1 {
            public static final String name = "images";
            public static final String originalFileName = "image1.jpg";
            public static final String contentType = MediaType.IMAGE_JPEG_VALUE;
            public static final byte[] content = "imageData1".getBytes();
        }
        public static class mockMultipartFile2 {
            public static final String name = "images";
            public static final String originalFileName = "image2.png";
            public static final String contentType = MediaType.IMAGE_JPEG_VALUE;
            public static final byte[] content = "imageData2".getBytes();
        }
    }

    private static void setImageFields(Image image, Long id, LocalDateTime createdDate) {
        ReflectionTestUtils.setField(image, "id", id);
        ReflectionTestUtils.setField(image, "createdDate", createdDate);
    }

    public static class member {
        public static class normal {
            public static class entity {
                public static MemberImage get() {
                    MemberImage ret = MemberImage.builder()
                        .name(R.member.normal.name)
                        .size(R.member.normal.size)
                        .extension(R.member.normal.extension)
                        .build();
                    setImageFields(ret, R.member.normal.id, R.member.normal.createdDate);
            //        ReflectionTestUtils.setField(ret, "baseMember", createValidMember());
                    return ret;
                }
            }
            public static class request {
                public static ImageRequest get() {
                    return ImageRequest.builder()
                        .name(R.member.normal.url)
                        .size(R.member.normal.size)
                        .extension(R.member.normal.extension)
                        .build();
                }
            }
            public static class response {

            }
        }
        public static class curator {
            public static class entity {
                public static MemberImage get() {
                    MemberImage ret = MemberImage.builder()
                        .name(R.member.curator.name)
                        .size(R.member.curator.size)
                        .extension(R.member.curator.extension)
                        .build();
                    setImageFields(ret, R.member.curator.id, R.member.curator.createdDate);
                    // ReflectionTestUtils.setField(ret, "baseMember", createValidCurator());
                    return ret;
                }
            }
            public static class request {

            }
            public static class response {

            }
        }
        public static class host {
            public static class entity {
                public static MemberImage get() {
                    MemberImage ret = MemberImage.builder()
                        .name(R.member.host.name)
                        .size(R.member.host.size)
                        .extension(R.member.host.extension)
                        .build();
                    setImageFields(ret, R.member.host.id, R.member.host.createdDate);
                    // ReflectionTestUtils.setField(ret, "baseMember", createValidHost());
                    return ret;
                }
            }
            public static class request {

            }
            public static class response {

            }
        }
        public static class welcome {
            public static class entity {
                public static MemberImage get() {
                    MemberImage ret = MemberImage.builder()
                        .name(R.member.welcome.name)
                        .size(R.member.welcome.size)
                        .extension(R.member.welcome.extension)
                        .build();
                    setImageFields(ret, R.member.welcome.id, R.member.welcome.createdDate);
                    // ReflectionTestUtils.setField(validMemberImage, "baseMember", createValidWelcome());
                    return ret;
                }
            }
            public static class request {

            }
            public static class response {

            }
        }
    }

    public static class event1 {
        public static class entity {
            public static EventImage get() {
                EventImage ret = EventImage.builder()
                    .name(R.event1.name)
                    .size(R.event1.size)
                    .extension(R.event1.extension)
                    .build();
                setImageFields(ret, R.event1.id, R.event1.createdDate);
//                ReflectionTestUtils.setField(ret, "event", createValidEvent());
                return ret;
            }
        }
        public static class request {
            public static ImageRequest get() {
                return ImageRequest.builder()
                    .name(R.event1.name)
                    .size(R.event1.size)
                    .extension(R.event1.extension)
                    .build();
            }
        }
        public static class response {

        }
    }
    public static class event2 {
        public static class entity {
            public static EventImage get() {
                EventImage ret = EventImage.builder()
                    .name(R.event2.name)
                    .size(R.event2.size)
                    .extension(R.event2.extension)
                    .build();
                setImageFields(ret, R.event2.id, R.event2.createdDate);
                // ReflectionTestUtils.setField(ret, "event", createValidEvent());
                return ret;
            }
        }
        public static class request {

        }
        public static class response {

        }
    }
    public static class event3 {
        public static class entity {
            public static EventImage get() {
                EventImage ret = EventImage.builder()
                    .name(R.event3.name)
                    .size(R.event3.size)
                    .extension(R.event3.extension)
                    .build();
                setImageFields(ret, R.event3.id, R.event3.createdDate);
                // ReflectionTestUtils.setField(ret, "event", createValidEvent());
                return ret;
            }
        }
        public static class request {

        }
        public static class response {

        }
    }
    public static class event4 {
        public static class entity {
            public static EventImage get() {
                EventImage ret = EventImage.builder()
                    .name(R.event4.name)
                    .size(R.event4.size)
                    .extension(R.event4.extension)
                    .build();
                setImageFields(ret, R.event4.id, R.event4.createdDate);
                // ReflectionTestUtils.setField(ret, "event", createValidEvent());
                return ret;
            }
        }
        public static class request {

        }
        public static class response {

        }
    }
    public static class event5 {
        public static class entity {
            public static EventImage get() {
                EventImage ret = EventImage.builder()
                    .name(R.event5.name)
                    .size(R.event5.size)
                    .extension(R.event5.extension)
                    .build();
                setImageFields(ret, R.event5.id, R.event5.createdDate);
                // ReflectionTestUtils.setField(ret, "event", createValidEvent());
                return ret;
            }
        }
        public static class request {

        }
        public static class response {

        }
    }

    public static class curation1 {
        public static class entity {
            public static CurationImage get() {
                CurationImage ret = CurationImage.builder()
                    .name(R.curation1.name)
                    .size(R.curation1.size)
                    .extension(R.curation1.extension)
                    .build();
                setImageFields(ret, R.curation1.id, R.curation1.createdDate);
                // ReflectionTestUtils.setField(ret, "curationCard", createValidCurator());
                return ret;
            }
        }
        public static class request {
            public static ImageRequest get() {
                return ImageRequest.builder()
                    .name(R.curation1.name)
                    .size(R.curation1.size)
                    .extension(R.curation1.extension)
                    .build();
            }
        }
        public static class response {
            public static ImageResponse get() {
                return ImageResponse.builder()
                    .id(R.curation1.id)
                    .url(R.curation1.url)
                    .size(R.curation1.size)
                    .extension(R.curation1.extension)
                    .createdDate(R.curation1.createdDate)
                    .build();
            }
        }
    }
    public static class curation2 {
        public static class entity {
            public static CurationImage get() {
                CurationImage ret = CurationImage.builder()
                    .name(R.curation2.name)
                    .size(R.curation2.size)
                    .extension(R.curation2.extension)
                    .build();
                setImageFields(ret, R.curation2.id, R.curation2.createdDate);
                // ReflectionTestUtils.setField(ret, "curationCard", createValidCurator());
                return ret;
            }
        }
        public static class request {
            public static ImageRequest get() {
                return ImageRequest.builder()
                    .name(R.curation2.name)
                    .size(R.curation2.size)
                    .extension(R.curation2.extension)
                    .build();
            }
        }
        public static class response {
            public static ImageResponse get() {
                return ImageResponse.builder()
                    .id(R.curation2.id)
                    .url(R.curation2.url)
                    .size(R.curation2.size)
                    .extension(R.curation2.extension)
                    .createdDate(R.curation2.createdDate)
                    .build();
            }
        }
    }
    public static class curation3 {
        public static class entity {
            public static CurationImage get() {
                CurationImage ret = CurationImage.builder()
                    .name(R.curation3.name)
                    .size(R.curation3.size)
                    .extension(R.curation3.extension)
                    .build();
                setImageFields(ret, R.curation3.id, R.curation3.createdDate);
                // ReflectionTestUtils.setField(ret, "curationCard", createValidCurator());
                return ret;
            }
        }
        public static class request {
            public static ImageRequest get() {
                return ImageRequest.builder()
                    .name(R.curation3.name)
                    .size(R.curation3.size)
                    .extension(R.curation3.extension)
                    .build();
            }
        }
        public static class response {
            public static ImageResponse get() {
                return ImageResponse.builder()
                    .id(R.curation3.id)
                    .url(R.curation3.url)
                    .size(R.curation3.size)
                    .extension(R.curation3.extension)
                    .createdDate(R.curation3.createdDate)
                    .build();
            }
        }
    }
    public static class curation4 {
        public static class entity {
            public static CurationImage get() {
                CurationImage ret = CurationImage.builder()
                    .name(R.curation4.name)
                    .size(R.curation4.size)
                    .extension(R.curation4.extension)
                    .build();
                setImageFields(ret, R.curation4.id, R.curation4.createdDate);
                // ReflectionTestUtils.setField(ret, "curationCard", createValidCurator());
                return ret;
            }
        }
        public static class request {
            public static ImageRequest get() {
                return ImageRequest.builder()
                    .name(R.curation4.name)
                    .size(R.curation4.size)
                    .extension(R.curation4.extension)
                    .build();
            }
        }
        public static class response {
            public static ImageResponse get() {
                return ImageResponse.builder()
                    .id(R.curation4.id)
                    .url(R.curation4.url)
                    .size(R.curation4.size)
                    .extension(R.curation4.extension)
                    .createdDate(R.curation4.createdDate)
                    .build();
            }
        }
    }
    public static class curation5 {
        public static class entity {
            public static CurationImage get() {
                CurationImage ret = CurationImage.builder()
                    .name(R.curation5.name)
                    .size(R.curation5.size)
                    .extension(R.curation5.extension)
                    .build();
                setImageFields(ret, R.curation5.id, R.curation5.createdDate);
                // ReflectionTestUtils.setField(ret, "curationCard", createValidCurator());
                return ret;
            }
        }
        public static class request {
            public static ImageRequest get() {
                return ImageRequest.builder()
                    .name(R.curation5.name)
                    .size(R.curation5.size)
                    .extension(R.curation5.extension)
                    .build();
            }
        }
        public static class response {
            public static ImageResponse get() {
                return ImageResponse.builder()
                    .id(R.curation5.id)
                    .url(R.curation5.url)
                    .size(R.curation5.size)
                    .extension(R.curation5.extension)
                    .createdDate(R.curation5.createdDate)
                    .build();
            }
        }
    }
    public static class curation6 {
        public static class entity {
            public static CurationImage get() {
                CurationImage ret = CurationImage.builder()
                    .name(R.curation6.name)
                    .size(R.curation6.size)
                    .extension(R.curation6.extension)
                    .build();
                setImageFields(ret, R.curation6.id, R.curation6.createdDate);
                // ReflectionTestUtils.setField(ret, "curationCard", createValidCurator());
                return ret;
            }
        }
        public static class request {
            public static ImageRequest get() {
                return ImageRequest.builder()
                    .name(R.curation6.name)
                    .size(R.curation6.size)
                    .extension(R.curation6.extension)
                    .build();
            }
        }
        public static class response {
            public static ImageResponse get() {
                return ImageResponse.builder()
                    .id(R.curation6.id)
                    .url(R.curation6.url)
                    .size(R.curation6.size)
                    .extension(R.curation6.extension)
                    .createdDate(R.curation6.createdDate)
                    .build();
            }
        }
    }
    public static class curation7 {
        public static class entity {
            public static CurationImage get() {
                CurationImage ret = CurationImage.builder()
                    .name(R.curation7.name)
                    .size(R.curation7.size)
                    .extension(R.curation7.extension)
                    .build();
                setImageFields(ret, R.curation7.id, R.curation7.createdDate);
                // ReflectionTestUtils.setField(ret, "curationCard", createValidCurator());
                return ret;
            }
        }
        public static class request {
            public static ImageRequest get() {
                return ImageRequest.builder()
                    .name(R.curation7.name)
                    .size(R.curation7.size)
                    .extension(R.curation7.extension)
                    .build();
            }
        }
        public static class response {
            public static ImageResponse get() {
                return ImageResponse.builder()
                    .id(R.curation7.id)
                    .url(R.curation7.url)
                    .size(R.curation7.size)
                    .extension(R.curation7.extension)
                    .createdDate(R.curation7.createdDate)
                    .build();
            }
        }
    }
    public static class curation8 {
        public static class entity {
            public static CurationImage get() {
                CurationImage ret = CurationImage.builder()
                    .name(R.curation8.name)
                    .size(R.curation8.size)
                    .extension(R.curation8.extension)
                    .build();
                setImageFields(ret, R.curation8.id, R.curation8.createdDate);
                // ReflectionTestUtils.setField(ret, "curationCard", createValidCurator());
                return ret;
            }
        }
        public static class request {
            public static ImageRequest get() {
                return ImageRequest.builder()
                    .name(R.curation8.name)
                    .size(R.curation8.size)
                    .extension(R.curation8.extension)
                    .build();
            }
        }
        public static class response {
            public static ImageResponse get() {
                return ImageResponse.builder()
                    .id(R.curation8.id)
                    .url(R.curation8.url)
                    .size(R.curation8.size)
                    .extension(R.curation8.extension)
                    .createdDate(R.curation8.createdDate)
                    .build();
            }
        }
    }

    public static class advertisement1 {
        public static class entity {
            public static AdvertisementImage get() {
                AdvertisementImage ret = AdvertisementImage.builder()
                    .name(R.advertisement1.name)
                    .size(R.advertisement1.size)
                    .extension(R.advertisement1.extension)
                    .build();
                setImageFields(ret, R.advertisement1.id, R.advertisement1.createdDate);
                // ReflectionTestUtils.setField(ret, "advertisement", createValidMember());
                return ret;
            }
        }
        public static class request {
            public static ImageRequest get() {
                return ImageRequest.builder()
                    .name(R.advertisement1.name)
                    .size(R.advertisement1.size)
                    .extension(R.advertisement1.extension)
                    .build();
            }
        }
        public static class response {
            public static ImageResponse get() {
                return ImageResponse.builder()
                    .id(R.advertisement1.id)
                    .url(R.advertisement1.url)
                    .size(R.advertisement1.size)
                    .extension(R.advertisement1.extension)
                    .createdDate(R.advertisement1.createdDate)
                    .build();
            }
        }
    }
    public static class advertisement2 {
        public static class entity {
            public static AdvertisementImage get() {
                AdvertisementImage ret = AdvertisementImage.builder()
                    .name(R.advertisement2.name)
                    .size(R.advertisement2.size)
                    .extension(R.advertisement2.extension)
                    .build();
                setImageFields(ret, R.advertisement2.id, R.advertisement2.createdDate);
                // ReflectionTestUtils.setField(ret, "advertisement", createValidMember());
                return ret;
            }
        }
        public static class request {
            public static ImageRequest get() {
                return ImageRequest.builder()
                    .name(R.advertisement2.name)
                    .size(R.advertisement2.size)
                    .extension(R.advertisement2.extension)
                    .build();
            }
        }
        public static class response {
            public static ImageResponse get() {
                return ImageResponse.builder()
                    .id(R.advertisement2.id)
                    .url(R.advertisement2.url)
                    .size(R.advertisement2.size)
                    .extension(R.advertisement2.extension)
                    .createdDate(R.advertisement2.createdDate)
                    .build();
            }
        }
    }
    public static class advertisement3 {
        public static class entity {
            public static AdvertisementImage get() {
                AdvertisementImage ret = AdvertisementImage.builder()
                    .name(R.advertisement3.name)
                    .size(R.advertisement3.size)
                    .extension(R.advertisement3.extension)
                    .build();
                setImageFields(ret, R.advertisement3.id, R.advertisement3.createdDate);
                // ReflectionTestUtils.setField(ret, "advertisement", createValidMember());
                return ret;
            }
        }
        public static class request {
            public static ImageRequest get() {
                return ImageRequest.builder()
                    .name(R.advertisement3.name)
                    .size(R.advertisement3.size)
                    .extension(R.advertisement3.extension)
                    .build();
            }
        }
        public static class response {
            public static ImageResponse get() {
                return ImageResponse.builder()
                    .id(R.advertisement3.id)
                    .url(R.advertisement3.url)
                    .size(R.advertisement3.size)
                    .extension(R.advertisement3.extension)
                    .createdDate(R.advertisement3.createdDate)
                    .build();
            }
        }
    }

    public static class mockMultipartFile1 {
        public static MockMultipartFile get() {
            return new MockMultipartFile(
                R.mockMultipartFile1.name,
                R.mockMultipartFile1.originalFileName,
                R.mockMultipartFile1.contentType,
                R.mockMultipartFile1.content);
        }
    }
    public static class mockMultipartFile2 {
        public static MockMultipartFile get() {
            return new MockMultipartFile(
                R.mockMultipartFile2.name,
                R.mockMultipartFile2.originalFileName,
                R.mockMultipartFile2.contentType,
                R.mockMultipartFile2.content);
        }
    }

}