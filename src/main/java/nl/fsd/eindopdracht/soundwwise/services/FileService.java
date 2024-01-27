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
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        if (!CheckAuthorization.isAuthorized(user, (Collection<GrantedAuthority>) authentication.getAuthorities(), authentication.getName())) {
//            throw new ForbiddenException("You're not allowed to add a photo to this profile.");
//        }

        // delete old profilepic if it exists, otherwise the server/folder gets filled up with a lot of unnecessary pictures
        if (user.getProfileImage() != null) {

            Path path = Paths.get(fileStorageLocation).toAbsolutePath().resolve(user.getFileName());

            try {
                Files.deleteIfExists(path);
            } catch (IOException e) {
                throw new RuntimeException("A problem occurred with deleting: " + user.getFileName());
            }
        }


//        String fileNameAddition = StringUtils.cleanPath(Objects.requireNonNull(String.valueOf(Date.from(Instant.now()).getTime()))); // to prevent that files can have the same name
        String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename() + String.valueOf(Date.from(Instant.now()).getTime()))); // added the datefrom etc so files can't have the same name and overwrite .

        Path filePath = Paths.get(fileStoragePath + File.separator + fileName);
//
//        String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
//
//        Path filePath = Paths.get(fileStoragePath + "/" + fileName);

        try {
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException("Issue in storing the file", e);
        }

        user.getProfileImage();
        user.setFileName(fileName);
        userRepository.save(user);

        return fileName;
    }

}
