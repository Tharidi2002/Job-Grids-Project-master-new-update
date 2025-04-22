package lk.ijse.backend.service.imple;

import lk.ijse.backend.service.FileStorageService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FileStorageServiceImpl implements FileStorageService {
    private static final String PROFILE_IMAGE_DIRECTORY = "profileImages\\";

    private static final String UPLOAD_DIRECTORY = "\\assets\\images\\backendImages\\";

    private static final String DEFAULT_DIRECTORY = "F:\\IJSE\\2nd\\jboNet\\Front-End" + UPLOAD_DIRECTORY;
    private static final String USER_PROFILE_UPLOAD_DIR = DEFAULT_DIRECTORY + PROFILE_IMAGE_DIRECTORY;

    static {
        createIfNotExistDirectory(USER_PROFILE_UPLOAD_DIR);
    }

    @Override
    public String storeProfileImage(MultipartFile file) throws IOException {
        if (file == null || file.isEmpty()) {
            return null;
        }

        String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
        Path filePath = Paths.get(USER_PROFILE_UPLOAD_DIR + fileName);

        Files.copy(file.getInputStream(), filePath);
        return UPLOAD_DIRECTORY + PROFILE_IMAGE_DIRECTORY + fileName;
    }

    @Override
    public boolean deleteProfileImage(String imagePath) {
        if (imagePath == null || imagePath.isEmpty()) {
            return false;
        }

        try {
            Path filePath = Paths.get("F:/IJSE/2nd/jboNet/Front-End/" + imagePath);
            return Files.deleteIfExists(filePath);
        } catch (IOException e) {
            return false;
        }
    }

    public static void createIfNotExistDirectory(String directory) {
        try {
            Files.createDirectories(Paths.get(directory));
        } catch (IOException e) {
            throw new RuntimeException("Could not create upload directory!", e);
        }
    }
}
