package org.ktc2.cokaen.wouldyouin.Image.application;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import org.ktc2.cokaen.wouldyouin._common.exception.FailedToDeleteImageException;
import org.ktc2.cokaen.wouldyouin._common.exception.FailedToUploadImageException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.multipart.MultipartFile;

@Component
public class ImageStorage {

    @Value("${image.upload.common-path}")
    private String commonPath;

    public String save(MultipartFile image, String subPath) {
        String fileName = "";
        try {
            fileName = generateUuidName() + "." + getExtension(image);
            Path path = Paths.get(commonPath, subPath, fileName);
            Files.createDirectories(path.getParent());
            Files.write(path, image.getBytes());
        } catch (IOException ex) {
            throw new FailedToUploadImageException();
        }
        return subPath + "/" + fileName;
    }

    // Todo: payment랑 이부분 restclient 유틸로 빼기
    public String save(String imageUrl, String subPath) {
        RestClient client = RestClient.builder().build();
        String fileName = "";
        try {
            ResponseEntity<byte[]> response = client.get()
                .uri(imageUrl)
                .retrieve()
                .toEntity(byte[].class);

            if (Optional.ofNullable(response).isPresent() && response.getStatusCode().is2xxSuccessful()) {
                byte[] imageBytes = response.getBody();

                fileName = generateUuidName() + "." + getExtension(imageUrl);
                Path path = Paths.get(commonPath, subPath, fileName);
                Files.createDirectories(path.getParent());
                Files.write(path, response.getBody());
            }
        } catch (IOException ex) {
            throw new FailedToUploadImageException();
        }
        return subPath + "/" + fileName;
    }

    public void delete(String imagePath) {
        try {
            Files.deleteIfExists(Paths.get(imagePath));
        } catch (IOException ex) {
            throw new FailedToDeleteImageException();
        }
    }

    protected static String getExtension(MultipartFile image) {
        return Objects.requireNonNull(image.getContentType()).split("/")[1];
    }

    protected static String getExtension(String imageUrl) {
        String[] splitted = imageUrl.split("\\.");
        return splitted[splitted.length - 1];
    }

    private static String generateUuidName() {
        return UUID.randomUUID().toString().replace("-", "");
    }
}