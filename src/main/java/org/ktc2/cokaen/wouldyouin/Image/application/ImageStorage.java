package org.ktc2.cokaen.wouldyouin.Image.application;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.UUID;
import org.ktc2.cokaen.wouldyouin.Image.api.ImageDomain;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class ImageStorage {

    @Value("${image.upload.common-path}")
    private String commonPath;

    public String save(MultipartFile image, String subPath) {
        String fileName = generateUuidName() + "." + getExtension(image);
        String pathStr = commonPath + subPath + fileName;
        Path path = Paths.get(pathStr);
        try {
            Files.createDirectories(path.getParent());
            Files.write(path, image.getBytes());
        } catch (IOException ex) {
            throw new RuntimeException("failed to upload image.");
        }
        return subPath + fileName;
    }

    public void delete(String imagePath) {
        try {
            Files.deleteIfExists(Paths.get(imagePath));
        } catch (IOException ex) {
            throw new RuntimeException("failed to delete image.");
        }
    }

    private static String getExtension(MultipartFile image) {
        return Objects.requireNonNull(image.getContentType()).split("/")[1];
    }

    private static String generateUuidName() {
        return UUID.randomUUID().toString().replace("-", "");
    }
}