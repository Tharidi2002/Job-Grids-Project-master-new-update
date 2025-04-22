package lk.ijse.backend.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FileStorageService {

    String storeProfileImage(MultipartFile file) throws IOException;

    boolean deleteProfileImage(String imagePath);
}
