package nl.fsd.eindopdracht.soundwwise.services;

import nl.fsd.eindopdracht.soundwwise.exceptions.RecordNotFoundException;
import nl.fsd.eindopdracht.soundwwise.models.User;
import nl.fsd.eindopdracht.soundwwise.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.Date;
import java.time.Instant;
import java.util.Objects;

@Service
public class FileService {

//    @Value("{src/resources/uploads}")
    private final Path fileStoragePath;
    private final String fileStorageLocation;
    private final UserRepository userRepository;

    public FileService(@Value("${file.storage.location}") String fileStorageLocation, UserRepository userRepository) {
        this.fileStoragePath = Paths.get(fileStorageLocation).toAbsolutePath().normalize();
        this.fileStorageLocation = fileStorageLocation;
        this.userRepository = userRepository;

        try {
            Files.createDirectories(fileStoragePath);
        } catch (IOException e) {
            throw new RuntimeException("Issue in creating file directory");
        }
    }

    public String storeFile(MultipartFile file, String url, Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RecordNotFoundException("The user with ID " + userId + " doesn't exist."));

        try {
            if (user.getProfileImage() != null) {
                Path path = Paths.get(fileStorageLocation).toAbsolutePath().resolve(user.getFileName());
                Files.deleteIfExists(path);
            }

            String fileName = StringUtils.cleanPath(file.getOriginalFilename() + String.valueOf(Date.from(Instant.now()).getTime()));
            Path filePath = Paths.get(fileStorageLocation).toAbsolutePath().resolve(fileName);

            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            user.setProfileImage(fileName);
            userRepository.save(user);

            return fileName;
        } catch (IOException e) {
            throw new RuntimeException("Issue in storing the file", e);
        }
    }

}
