package org.ktc2.cokaen.wouldyouin._common.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;
import org.ktc2.cokaen.wouldyouin._common.exception.ExtensionParsingException;
import org.ktc2.cokaen.wouldyouin._common.exception.FailToReadImageException;
import org.ktc2.cokaen.wouldyouin._common.exception.FailedToUploadImageException;
import org.springframework.web.multipart.MultipartFile;

public class FileUtil {

    public static byte[] readFile(Path path) {
        try {
            return Files.readAllBytes(path);
        } catch (IOException e) {
            throw new FailToReadImageException("파일을 읽어오는데 실패했습니다.");
        }
    }

    public static void saveFile(MultipartFile file, Path path) {
        try {
            Files.createDirectories(path.getParent());
            Files.write(path, file.getBytes());
        } catch (IOException e) {
            throw new FailedToUploadImageException("디렉토리에 파일을 저장하는데 실패했습니다.");
        }
    }

    public static void saveFile(byte[] file, Path path) {
        try {
            Files.createDirectories(path.getParent());
            Files.write(path, file);
        } catch (IOException e) {
            throw new FailedToUploadImageException("디렉토리에 파일을 저장하는데 실패했습니다.");
        }
    }

    public static void deleteFile(Path path) {
        try {
            Files.deleteIfExists(path);
        } catch (IOException e) {
            throw new FailedToUploadImageException("해당 경로의 파일을 삭제하는데 실패했습니다.");
        }
    }

    public static String generateUuidName() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    public static String getExtension(MultipartFile file) {
        if (file == null || file.getContentType() == null) {
            throw new ExtensionParsingException("파일이 존재하지 않거나 콘텐츠 타입이 없습니다.");
        }
        try {
            return file.getContentType().split("/")[1];
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new ExtensionParsingException("파일의 확장자를 찾을 수 없습니다.");
        }
    }

    public static String getExtension(String url) {
        if (url == null || !url.contains(".")) {
            throw new ExtensionParsingException("URL이 존재하지 않거나 확장자가 없습니다.");
        }
        return url.substring(url.lastIndexOf('.') + 1);
    }
}