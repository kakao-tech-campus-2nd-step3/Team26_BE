package org.ktc2.cokaen.wouldyouin.Image.application;

import java.nio.file.Path;
import java.nio.file.Paths;
import lombok.RequiredArgsConstructor;
import org.ktc2.cokaen.wouldyouin.Image.api.dto.ImageRequest;
import org.ktc2.cokaen.wouldyouin._common.exception.FailedToUploadImageException;
import org.ktc2.cokaen.wouldyouin._common.util.FileUtil;
import org.ktc2.cokaen.wouldyouin._common.util.RestClientUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
@RequiredArgsConstructor
public class ImageStorageService {

    @Value("${image.upload.parent-path}")
    private String parentPath;
    private final RestClientUtil client;

    public byte[] readFromDirectory(Path path) {
        return FileUtil.readFile(Paths.get(parentPath).resolve(path));
    }

    public ImageRequest saveToDirectory(MultipartFile image, String subPath) {
        String extension = FileUtil.getExtension(image);
        String fileName = FileUtil.generateUuidName() + "." + extension;
        Path path = Paths.get(parentPath, subPath, fileName);
        FileUtil.saveFile(image, path);
        return ImageRequest.of(path.toString(), image.getSize(), FileUtil.getExtension(image));
    }

    public ImageRequest saveToDirectory(String imageUrl, String subPath) {
        ResponseEntity<byte[]> response = client.get(imageUrl, byte[].class);
        if (response.getBody() == null) {
            throw new FailedToUploadImageException("응답 본문이 비어있어 이미지를 가져올 수 없습니다.");
        }
        if (!response.getStatusCode().is2xxSuccessful()) {
            throw new FailedToUploadImageException("이미지 URL에 대한 요청을 실패하였습니다.");
        }
        String extension = FileUtil.getExtension(imageUrl);
        String fileName = FileUtil.generateUuidName() + "." + extension;
        Path path = Paths.get(parentPath, subPath, fileName);
        FileUtil.saveFile(response.getBody(), path);
        long size = response.getBody().length;
        return ImageRequest.of(path.toString(), size, extension);
    }

    public void delete(String imagePath) {
        FileUtil.deleteFile(Paths.get(imagePath));
    }
}