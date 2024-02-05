package nl.fsd.eindopdracht.soundwwise.services;

import nl.fsd.eindopdracht.soundwwise.exceptions.RecordNotFoundException;
import nl.fsd.eindopdracht.soundwwise.models.Project;
import nl.fsd.eindopdracht.soundwwise.models.Song;
import nl.fsd.eindopdracht.soundwwise.models.User;
import nl.fsd.eindopdracht.soundwwise.repositories.ProjectRepository;
import nl.fsd.eindopdracht.soundwwise.repositories.SongRepository;
import nl.fsd.eindopdracht.soundwwise.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.Date;
import java.time.Instant;

@Service
public class FileService {

    private final Path fileStoragePath;
    private final String fileStorageLocation;
    private final UserRepository userRepository;
    private final SongRepository songRepository;
    private final ProjectRepository projectRepository;

    public FileService(@Value("${file.storage.location}") String fileStorageLocation, UserRepository userRepository, SongRepository songRepository, ProjectRepository projectRepository) {
        this.fileStoragePath = Paths.get(fileStorageLocation).toAbsolutePath().normalize();
        this.fileStorageLocation = fileStorageLocation;
        this.userRepository = userRepository;
        this.songRepository = songRepository;
        this.projectRepository = projectRepository;

        try {
            Files.createDirectories(fileStoragePath);
        } catch (IOException e) {
            throw new RuntimeException("Issue in creating file directory");
        }
    }


    ////////////////////////////////
    //PROFILE IMAGE
    ////////////////////////////////

    public String storeFile(MultipartFile file, String url, Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RecordNotFoundException("The user with ID " + userId + " doesn't exist."));

        try {
            if (user.getProfileImage() != null) {
                Path path = Paths.get(fileStorageLocation).toAbsolutePath().resolve(user.getProfileImage());
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

    public Resource getFile(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RecordNotFoundException("User with ID: " + userId + " doesn't exist."));
        Path path = Paths.get(fileStorageLocation).toAbsolutePath().resolve(user.getProfileImage());
        Resource resource;
        try {
            resource = new UrlResource(path.toUri());
        } catch (MalformedURLException e) {
            throw new RuntimeException("Issue in reading the file", e);
        }
        return resource;

    }

    public boolean deleteProfilePic(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RecordNotFoundException("The user with ID " + userId + " doesn't exist."));
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        if (!CheckAuthorization.isAuthorized(user, (Collection<GrantedAuthority>) authentication.getAuthorities(), authentication.getName())) {
//            throw new ForbiddenException("You're not allowed to delete the photo of this profile.");
//        }
        Path path = Paths.get(fileStorageLocation).toAbsolutePath().resolve(user.getProfileImage());

        user.setProfileImage(null);
        userRepository.save(user);

        try {
            return Files.deleteIfExists(path);
        } catch (IOException e) {
            throw new RuntimeException("A problem occurred with deleting: " + user.getProfileImage());
        }


    }

    ////////////////////////////////
    //SONG
    ////////////////////////////////

    public String storeSong(MultipartFile file, String url, Long songId) {
        Song song = songRepository.findById(songId).orElseThrow(() -> new RecordNotFoundException(""));

        try {
            if (song.getSongUrl() != null) {
                Path path = Paths.get(fileStorageLocation).toAbsolutePath().resolve(song.getSongUrl());
                Files.deleteIfExists(path);
            }

            String fileName = StringUtils.cleanPath(file.getOriginalFilename() + String.valueOf(Date.from(Instant.now()).getTime()));
            Path filePath = Paths.get(fileStorageLocation).toAbsolutePath().resolve(fileName);

            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            song.setSongUrl(fileName);
            songRepository.save(song);

            return fileName;
        } catch (IOException e) {
            throw new RuntimeException("Issue in storing the file", e);
        }
    }

    public Resource getSong(Long songId) {
        Song song = songRepository.findById(songId).orElseThrow(() -> new RecordNotFoundException(""));
        Path path = Paths.get(fileStorageLocation).toAbsolutePath().resolve(song.getSongUrl());
        Resource resource;
        try {
            resource = new UrlResource(path.toUri());
        } catch (MalformedURLException e) {
            throw new RuntimeException("Issue in reading the file", e);
        }
        return resource;

    }


    public boolean deleteSong(Long songId) {
        Song song = songRepository.findById(songId).orElseThrow(() -> new RecordNotFoundException(""));
        Path path = Paths.get(fileStorageLocation).toAbsolutePath().resolve(song.getSongUrl());
        song.setSongUrl(null);
        songRepository.save(song);
        try {
            return Files.deleteIfExists(path);
        } catch (IOException e) {
            throw new RuntimeException("");
        }


    }

    ////////////////////////////////
    //PROFILE IMAGE
    ////////////////////////////////

    public String storeProjectImage(MultipartFile file, String url, Long projectId) {
        Project project = projectRepository.findById(projectId).orElseThrow(() -> new RecordNotFoundException(""));

        try {
            if (project.getProjectCoverImage() != null) {
                Path path = Paths.get(fileStorageLocation).toAbsolutePath().resolve(project.getProjectCoverImage());
                Files.deleteIfExists(path);
            }

            String fileName = StringUtils.cleanPath(file.getOriginalFilename() + String.valueOf(Date.from(Instant.now()).getTime()));
            Path filePath = Paths.get(fileStorageLocation).toAbsolutePath().resolve(fileName);

            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            project.setProjectCoverImage(fileName);
            projectRepository.save(project);

            return fileName;
        } catch (IOException e) {
            throw new RuntimeException("Issue in storing the file", e);
        }
    }


}
