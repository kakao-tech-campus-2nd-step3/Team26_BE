package org.ktc2.cokaen.wouldyouin.Image.application;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.ktc2.cokaen.wouldyouin.Image.api.dto.ImageRequest;
import org.ktc2.cokaen.wouldyouin._common.exception.FailedToUploadImageException;
import org.ktc2.cokaen.wouldyouin._common.util.FileUtil;
import org.ktc2.cokaen.wouldyouin._common.util.RestClientUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
@RequiredArgsConstructor
public class ImageStorageService {

    @Value("${image.upload.parent-path}")
    private String parentPath;
    private final RestClientUtil client;

    public byte[] readFromDirectory(Path childPath) {
        return FileUtil.readFile(Paths.get(parentPath).resolve(childPath));
    }

    public ImageRequest saveToDirectory(MultipartFile image, String childPath) {
        String extension = FileUtil.getExtension(image);
        String fileName = FileUtil.createRandomFileName(extension);
        FileUtil.saveFile(image, Path.of(parentPath, childPath, fileName));
        return ImageRequest.of(fileName, image.getSize(), extension);
    }

    public ImageRequest saveToDirectory(String imageUrl, String childPath) {
        byte[] response = client.get(byte[].class, imageUrl, new HttpHeaders(),
            (req, rsp) -> { throw new FailedToUploadImageException("이미지 URL에 대한 요청을 실패하였습니다."); }
        );
        Optional.ofNullable(response).orElseThrow(
            () -> new FailedToUploadImageException("응답 본문이 비어있어 이미지를 가져올 수 없습니다.")
        );
        String extension = FileUtil.getExtension(imageUrl);
        String fileName = FileUtil.createRandomFileName(extension);
        Path path = Path.of(parentPath, childPath, fileName);
        FileUtil.saveFile(response, path);
        return ImageRequest.of(fileName, (long) response.length, extension);
    }

    public void delete(String childPath, String fileName) {
        FileUtil.deleteFile(Path.of(parentPath, childPath, fileName));
    }
}