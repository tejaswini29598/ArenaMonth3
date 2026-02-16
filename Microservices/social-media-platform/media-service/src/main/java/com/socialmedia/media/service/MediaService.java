package com.socialmedia.media.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Slf4j
@Service
public class MediaService {

    private static final String UPLOAD_DIR = "/tmp/media";

    public String uploadMedia(MultipartFile file) throws IOException {
        String filename = UUID.randomUUID() + "_" + file.getOriginalFilename();
        Path uploadPath = Paths.get(UPLOAD_DIR, filename);
        
        Files.createDirectories(uploadPath.getParent());
        Files.write(uploadPath, file.getBytes());
        
        log.info("Media uploaded: {}", filename);
        return "/media/files/" + filename;
    }

    public byte[] getMedia(String filename) throws IOException {
        Path filePath = Paths.get(UPLOAD_DIR, filename);
        return Files.readAllBytes(filePath);
    }

    public void deleteMedia(String filename) throws IOException {
        Path filePath = Paths.get(UPLOAD_DIR, filename);
        Files.deleteIfExists(filePath);
        log.info("Media deleted: {}", filename);
    }
}
