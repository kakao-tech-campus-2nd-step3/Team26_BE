package org.ktc2.cokaen.wouldyouin.image.application;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import net.coobird.thumbnailator.Thumbnails;
import org.ktc2.cokaen.wouldyouin._common.exception.FailedToUploadImageException;
import org.ktc2.cokaen.wouldyouin._common.util.FileUtil;
import org.ktc2.cokaen.wouldyouin._common.util.RestClientUtil;
import org.ktc2.cokaen.wouldyouin._common.util.UriUtil;
import org.ktc2.cokaen.wouldyouin.image.api.dto.ImageRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
@RequiredArgsConstructor
public class ImageStorageService {

    @Value("${image.upload.parent-path}")
    private String parentPath;

    @Value("${image.upload.thumbnail.child-path}")
    private String thumbnailChildPath;

    @Value("${image.upload.thumbnail.height}")
    private Integer thumbnailHeight;

    @Value("${image.upload.thumbnail.width}")
    private Integer thumbnailWidth;

    @Value("${image.upload.thumbnail.extension}")
    private String thumbnailExtension;

    private final RestClientUtil client;

    public byte[] readFromDirectory(Path childPath) {
        return FileUtil.readFile(Paths.get(parentPath).resolve(childPath));
    }

    public ImageRequest saveToDirectory(MultipartFile image, String childPath) {
        String extension = FileUtil.getExtension(image.getContentType());
        String fileName = FileUtil.createRandomFileName(extension);
        FileUtil.saveFile(image, Path.of(parentPath, childPath, fileName));
        return ImageRequest.of(fileName, image.getSize(), extension);
    }

    public ImageRequest saveToDirectory(String imageUrl, String childPath) {
        ResponseEntity<byte[]> response = client.getResponseEntity(byte[].class, imageUrl, new HttpHeaders(),
            (req, rsp) -> {
                throw new FailedToUploadImageException("이미지 URL에 대한 요청을 실패하였습니다.");
            }
        );

        String contentType = Optional.ofNullable(response.getHeaders().getContentType()).orElseThrow(
            () -> new FailedToUploadImageException("응답 헤더에 콘텐츠 타입이 없어 이미지를 가져올 수 없습니다.")
        ).toString();
        byte[] body = Optional.ofNullable(response.getBody()).orElseThrow(
            () -> new FailedToUploadImageException("응답 본문이 비어있어 이미지를 가져올 수 없습니다.")
        );

        String extension = FileUtil.getExtension(contentType);
        String fileName = FileUtil.createRandomFileName(extension);
        Path path = Path.of(parentPath, childPath, fileName);
        FileUtil.saveFile(response.getBody(), path);
        return ImageRequest.of(fileName, (long)body.length, extension);
    }

    // TODO : 썸네일 생성 코드 리팩토링, 파일 유틸로 이동
    public String createThumbnailImage(String apiHeader, String childPath, String originFileName) {
        String fileName = originFileName;
        String originImagePath = Path.of(parentPath, childPath, originFileName).toString();
        String thumbnailImagePath = Path.of(parentPath, childPath, thumbnailChildPath).toString();
        try {
            Files.createDirectories(Paths.get(thumbnailImagePath));
            Thumbnails.of(new File(originImagePath))
                .size(thumbnailHeight, thumbnailWidth)
                .toFile(new File(thumbnailImagePath, fileName));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return UriUtil.assembleFullUrl(apiHeader,childPath, thumbnailChildPath, fileName);
    }

    public void delete(String childPath, String fileName) {
        FileUtil.deleteFile(Path.of(parentPath, childPath, fileName));
    }
}